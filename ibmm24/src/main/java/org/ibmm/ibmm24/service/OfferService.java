package org.ibmm.ibmm24.service;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OfferService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String cleanSqlString(String sql) {
        return sql.replaceAll("\\R", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String getOffers(
            int regionID,
            int timeRangeStart,
            int timeRangeEnd,
            int numberDays,
            String sortOrder,
            int pageSize,
            int offset,
            int priceRangeWidth,
            int minFreeKilometerWidth,
            Integer minNumberSeats,
            Integer minPrice,
            Integer maxPrice,
            String carType,
            Boolean onlyVollkasko,
            Integer minFreeKilometer) {

        String sortOrderSql = "DESC";
        if ("price-asc".equalsIgnoreCase(sortOrder)) {
            sortOrderSql = "ASC";
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("regionID", regionID)
                .addValue("timeRangeStart", timeRangeStart)
                .addValue("timeRangeEnd", timeRangeEnd)
                .addValue("numberDays", numberDays)
                .addValue("sortOrder", sortOrderSql)
                .addValue("pageSize", pageSize)
                .addValue("offset", offset)
                .addValue("priceRangeWidth", priceRangeWidth)
                .addValue("minFreeKilometerWidth", minFreeKilometerWidth)
                .addValue("minNumberSeats", minNumberSeats)
                .addValue("minPrice", minPrice)
                .addValue("maxPrice", maxPrice)
                .addValue("carType", carType)
                .addValue("onlyVollkasko", onlyVollkasko)
                .addValue("minFreeKilometer", minFreeKilometer);

        String sql = "WITH RECURSIVE " +
                "region_path AS (" +
                "SELECT path FROM regions WHERE id = :regionID" +
                "), " +
                "filtered_offers AS (" +
                "SELECT " +
                "o.id AS offer_id, " +
                "o.data, " +
                "o.price, " +
                "o.car_type, " +
                "o.number_seats, " +
                "o.free_kilometers, " +
                "o.has_vollkasko " +
                "FROM offers o " +
                "JOIN regions r ON o.most_specific_region = r.id " +
                "WHERE r.path <@ (SELECT path FROM region_path) " +
                "AND NOT EXISTS (SELECT 1 FROM regions r2 WHERE r2.path <@ r.path AND r2.path != r.path) " +
                "AND o.start_date >= :timeRangeStart " +
                "AND o.end_date <= :timeRangeEnd " +
                "AND o.full_days >= :numberDays" +
                "), " +
                "filtered_offers_all AS (" +
                "SELECT *, price as sort_price " +
                "FROM filtered_offers " +
                "WHERE (:minNumberSeats IS NULL OR number_seats >= :minNumberSeats) " +
                "AND (:minPrice IS NULL OR price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR price <= :maxPrice) " +
                "AND (:carType IS NULL OR car_type = :carType) " +
                "AND (:onlyVollkasko IS NULL OR (CASE WHEN :onlyVollkasko THEN has_vollkasko ELSE TRUE END)) " +
                "AND (:minFreeKilometer IS NULL OR free_kilometers >= :minFreeKilometer)" +
                "), " +
                "filtered_offers_for_vollkasko AS (" +
                "SELECT * " +
                "FROM filtered_offers " +
                "WHERE (:minNumberSeats IS NULL OR number_seats >= :minNumberSeats) " +
                "AND (:minPrice IS NULL OR price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR price <= :maxPrice) " +
                "AND (:carType IS NULL OR car_type = :carType) " +
                "AND (:minFreeKilometer IS NULL OR free_kilometers >= :minFreeKilometer)" +
                "), " +
                "filtered_offers_for_seats AS (" +
                "SELECT * " +
                "FROM filtered_offers " +
                "WHERE (:minPrice IS NULL OR price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR price <= :maxPrice) " +
                "AND (:carType IS NULL OR car_type = :carType) " +
                "AND (:onlyVollkasko IS NULL OR (CASE WHEN :onlyVollkasko THEN has_vollkasko ELSE TRUE END)) " +
                "AND (:minFreeKilometer IS NULL OR free_kilometers >= :minFreeKilometer)" +
                "), " +
                "filtered_offers_for_car_type AS (" +
                "SELECT * " +
                "FROM filtered_offers " +
                "WHERE (:minNumberSeats IS NULL OR number_seats >= :minNumberSeats) " +
                "AND (:minPrice IS NULL OR price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR price <= :maxPrice) " +
                "AND (:onlyVollkasko IS NULL OR (CASE WHEN :onlyVollkasko THEN has_vollkasko ELSE TRUE END)) " +
                "AND (:minFreeKilometer IS NULL OR free_kilometers >= :minFreeKilometer)" +
                "), " +
                "price_ranges AS (" +
                "SELECT " +
                "floor(price::numeric / :priceRangeWidth) AS bucket, " +
                "COUNT(*) AS count " +
                "FROM filtered_offers_all " +
                "GROUP BY floor(price::numeric / :priceRangeWidth) " +
                "HAVING COUNT(*) > 0" +
                "), " +
                "car_type_counts AS (" +
                "SELECT car_type, COUNT(*) AS count " +
                "FROM filtered_offers_for_car_type " +
                "GROUP BY car_type " +
                "HAVING COUNT(*) > 0" +
                "), " +
                "seats_count AS (" +
                "SELECT number_seats, COUNT(*) AS count " +
                "FROM filtered_offers_for_seats " +
                "GROUP BY number_seats " +
                "HAVING COUNT(*) > 0" +
                "), " +
                "kilometer_ranges AS (" +
                "SELECT " +
                "floor(free_kilometers::numeric / :minFreeKilometerWidth) AS bucket, " +
                "COUNT(*) AS count " +
                "FROM filtered_offers_all " +
                "GROUP BY floor(free_kilometers::numeric / :minFreeKilometerWidth) " +
                "HAVING COUNT(*) > 0" +
                "), " +
                "vollkasko_counts AS (" +
                "SELECT " +
                "NULLIF(SUM(CASE WHEN has_vollkasko THEN 1 ELSE 0 END), 0) AS true_count, " +
                "NULLIF(SUM(CASE WHEN NOT has_vollkasko THEN 1 ELSE 0 END), 0) AS false_count " +
                "FROM filtered_offers_for_vollkasko" +
                ") " +
                "SELECT json_build_object(" +
                "'offers', (" +
                "SELECT json_agg(json_build_object('ID', offer_id, 'data', data)) " +
                "FROM (" +
                "SELECT offer_id, data " +
                "FROM filtered_offers_all " +
                "ORDER BY " +
                "CASE WHEN :sortOrder = 'DESC' THEN sort_price END DESC, " +
                "CASE WHEN :sortOrder = 'ASC' THEN sort_price END ASC, " +
                "offer_id " +
                "LIMIT :pageSize OFFSET :offset" +
                ") o" +
                "), " +
                "'priceRanges', (" +
                "SELECT json_agg(t) " +
                "FROM (" +
                "SELECT " +
                "bucket * :priceRangeWidth AS start, " +
                "(bucket + 1) * :priceRangeWidth AS end, " +
                "count " +
                "FROM price_ranges " +
                "ORDER BY bucket" +
                ") t" +
                "), " +
                "'carTypeCounts', (" +
                "SELECT json_object_agg(car_type, count) " +
                "FROM (" +
                "SELECT car_type, count " +
                "FROM car_type_counts " +
                "ORDER BY car_type" +
                ") t" +
                "), " +
                "'seatsCount', (" +
                "SELECT json_agg(json_build_object('numberSeats', number_seats, 'count', count)) " +
                "FROM (" +
                "SELECT number_seats, count " +
                "FROM seats_count " +
                "ORDER BY number_seats" +
                ") t" +
                "), " +
                "'freeKilometerRange', (" +
                "SELECT json_agg(t) " +
                "FROM (" +
                "SELECT " +
                "bucket * :minFreeKilometerWidth AS start, " +
                "(bucket + 1) * :minFreeKilometerWidth AS end, " +
                "count " +
                "FROM kilometer_ranges " +
                "ORDER BY bucket" +
                ") t" +
                "), " +
                "'vollkaskoCount', (" +
                "SELECT json_build_object('trueCount', coalesce(true_count, 0), 'falseCount', coalesce(false_count, 0) " +
                "FROM vollkasko_counts " +
                ")" +
                ") AS result";

        String cleanedSql = cleanSqlString(sql);

        try {
            return jdbcTemplate.queryForObject(cleanedSql, params, String.class);
        } catch (Exception e) {
            System.err.println("Executing SQL: " + cleanedSql);
            throw e;
        }
    }
}