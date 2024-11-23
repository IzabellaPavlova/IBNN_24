WITH RECURSIVE region_path AS (
    SELECT path
    FROM regions
    WHERE id = $1 -- regionID
),
filtered_offers AS (
    SELECT
        o.id AS offer_id,
        o.data,
        o.price,
        o.car_type,
        o.number_seats,
        o.free_kilometers,
        o.has_vollkasko
    FROM offers o
    JOIN regions r ON o.most_specific_region = r.id
    WHERE r.path <@ (SELECT path FROM region_path)
    AND NOT EXISTS (
        SELECT 1
        FROM regions r2
        WHERE r2.path <@ r.path
        AND r2.path != r.path
    )
    AND o.start_date >= $2 -- timeRangeStart
    AND o.end_date <= $3 -- timeRangeEnd
    AND o.full_days >= $4 -- numberDays
),
-- Base filtered offers with all optional filters
filtered_offers_all AS (
    SELECT *
    FROM filtered_offers
    WHERE ($10 IS NULL OR number_seats >= $10) -- minNumberSeats
    AND ($11 IS NULL OR price >= $11) -- minPrice
    AND ($12 IS NULL OR price <= $12) -- maxPrice
    AND ($13 IS NULL OR car_type = $13) -- carType
    AND ($14 IS NULL OR (CASE WHEN $14 THEN has_vollkasko ELSE TRUE END)) -- onlyVollkasko
    AND ($15 IS NULL OR free_kilometers >= $15) -- minFreeKilometer
),
-- Filtered offers excluding vollkasko filter for vollkasko counts
filtered_offers_for_vollkasko AS (
    SELECT *
    FROM filtered_offers
    WHERE ($10 IS NULL OR number_seats >= $10)
    AND ($11 IS NULL OR price >= $11)
    AND ($12 IS NULL OR price <= $12)
    AND ($13 IS NULL OR car_type = $13)
    AND ($15 IS NULL OR free_kilometers >= $15)
),
-- Filtered offers excluding seats filter for seats count
filtered_offers_for_seats AS (
    SELECT *
    FROM filtered_offers
    WHERE ($11 IS NULL OR price >= $11)
    AND ($12 IS NULL OR price <= $12)
    AND ($13 IS NULL OR car_type = $13)
    AND ($14 IS NULL OR (CASE WHEN $14 THEN has_vollkasko ELSE TRUE END))
    AND ($15 IS NULL OR free_kilometers >= $15)
),
-- Filtered offers excluding car type filter for car type counts
filtered_offers_for_car_type AS (
    SELECT *
    FROM filtered_offers
    WHERE ($10 IS NULL OR number_seats >= $10)
    AND ($11 IS NULL OR price >= $11)
    AND ($12 IS NULL OR price <= $12)
    AND ($14 IS NULL OR (CASE WHEN $14 THEN has_vollkasko ELSE TRUE END))
    AND ($15 IS NULL OR free_kilometers >= $15)
),
-- Price range aggregation
price_ranges AS (
    SELECT
        floor(price::numeric / $8) AS bucket,
        COUNT(*) AS count
    FROM filtered_offers_all
    GROUP BY floor(price::numeric / $8)
    HAVING COUNT(*) > 0
),
-- Car type counts (excluding car type filter)
car_type_counts AS (
    SELECT
        car_type,
        COUNT(*) AS count
    FROM filtered_offers_for_car_type
    GROUP BY car_type
    HAVING COUNT(*) > 0
),
-- Seats count (excluding seats filter)
seats_count AS (
    SELECT
        number_seats,
        COUNT(*) AS count
    FROM filtered_offers_for_seats
    GROUP BY number_seats
    HAVING COUNT(*) > 0
),
-- Free kilometer ranges
kilometer_ranges AS (
    SELECT
        floor(free_kilometers::numeric / $9) AS bucket,
        COUNT(*) AS count
    FROM filtered_offers_all
    GROUP BY floor(free_kilometers::numeric / $9)
    HAVING COUNT(*) > 0
),
-- Vollkasko counts (excluding vollkasko filter)
vollkasko_counts AS (
    SELECT
        NULLIF(SUM(CASE WHEN has_vollkasko THEN 1 ELSE 0 END), 0) AS true_count,
        NULLIF(SUM(CASE WHEN NOT has_vollkasko THEN 1 ELSE 0 END), 0) AS false_count
    FROM filtered_offers_for_vollkasko
)
SELECT json_build_object(
    'offers', (
        SELECT json_agg(json_build_object(
            'ID', offer_id,
            'data', data
        ))
        FROM (
            SELECT offer_id, data
            FROM filtered_offers_all
            ORDER BY
                CASE WHEN $5 = 'DESC' THEN price END DESC,
                CASE WHEN $5 = 'ASC' THEN price END ASC,
                offer_id
            LIMIT $6 -- pageSize
            OFFSET $7 -- page * pageSize
        ) o
    ),
    'priceRanges', (
        SELECT json_agg(t)
        FROM (
            SELECT
                bucket * $8 AS start,
                (bucket + 1) * $8 AS end,
                count
            FROM price_ranges
            ORDER BY bucket
        ) t
    ),
    'carTypeCounts', (
        SELECT json_object_agg(
            car_type,
            count
        )
        FROM (
            SELECT car_type, count
            FROM car_type_counts
            ORDER BY car_type
        ) t
    ),
    'seatsCount', (
        SELECT json_agg(json_build_object(
            'numberSeats', number_seats,
            'count', count
        ))
        FROM (
            SELECT number_seats, count
            FROM seats_count
            ORDER BY number_seats
        ) t
    ),
    'freeKilometerRange', (
        SELECT json_agg(t)
        FROM (
            SELECT
                bucket * $9 AS start,
                (bucket + 1) * $9 AS end,
                count
            FROM kilometer_ranges
            ORDER BY bucket
        ) t
    ),
    'vollkaskoCount', (
        SELECT json_build_object(
            'trueCount', true_count,
            'falseCount', false_count
        )
        FROM vollkasko_counts
        WHERE true_count IS NOT NULL OR false_count IS NOT NULL
    )
) AS result;
