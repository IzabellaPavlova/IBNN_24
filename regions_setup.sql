-- Enable ltree extension
CREATE EXTENSION IF NOT EXISTS ltree;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS offers;
DROP TABLE IF EXISTS regions;

-- Create regions table
CREATE TABLE regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    path LTREE NOT NULL UNIQUE
);

-- Create offers table
CREATE TABLE Offers (
    id SERIAL PRIMARY KEY,
    most_specific_region_path LTREE NOT NULL REFERENCES Regions(path) ON DELETE CASCADE,
    data JSONB NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_seats INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    car_type VARCHAR(255) NOT NULL,
    has_vollkasko BOOLEAN NOT NULL,
    free_kilometers INT NOT NULL
);

-- Create indexes
CREATE INDEX path_gist_idx ON regions USING GIST (path);
CREATE INDEX path_btree_idx ON regions USING BTREE (path);
CREATE INDEX subregion_idx ON offers(most_specific_region_path);

-- Insert data
INSERT INTO regions (id, name, path) VALUES (0, 'European Union', '0');
INSERT INTO regions (id, name, path) VALUES (1, 'Germany', '0.1');
INSERT INTO regions (id, name, path) VALUES (7, 'Berlin', '0.1.7');
INSERT INTO regions (id, name, path) VALUES (21, 'Mitte', '0.1.7.21');
INSERT INTO regions (id, name, path) VALUES (58, 'Brandenburg Gate', '0.1.7.21.58');
INSERT INTO regions (id, name, path) VALUES (59, 'Berlin Cathedral', '0.1.7.21.59');
INSERT INTO regions (id, name, path) VALUES (22, 'Kreuzberg', '0.1.7.22');
INSERT INTO regions (id, name, path) VALUES (60, 'East Side Gallery', '0.1.7.22.60');
INSERT INTO regions (id, name, path) VALUES (61, 'Checkpoint Charlie', '0.1.7.22.61');
INSERT INTO regions (id, name, path) VALUES (23, 'Berlin Brandenburg Airport', '0.1.7.23');
INSERT INTO regions (id, name, path) VALUES (62, 'Terminal A', '0.1.7.23.62');
INSERT INTO regions (id, name, path) VALUES (63, 'Terminal B', '0.1.7.23.63');
INSERT INTO regions (id, name, path) VALUES (8, 'Munich', '0.1.8');
INSERT INTO regions (id, name, path) VALUES (24, 'Maxvorstadt', '0.1.8.24');
INSERT INTO regions (id, name, path) VALUES (64, 'Oper Munich', '0.1.8.24.64');
INSERT INTO regions (id, name, path) VALUES (65, 'University of Munich', '0.1.8.24.65');
INSERT INTO regions (id, name, path) VALUES (25, 'Neuhausen Nymphenburg', '0.1.8.25');
INSERT INTO regions (id, name, path) VALUES (66, 'Nymphenburg Palace', '0.1.8.25.66');
INSERT INTO regions (id, name, path) VALUES (67, 'CHECK24 Office', '0.1.8.25.67');
INSERT INTO regions (id, name, path) VALUES (26, 'Schwabing', '0.1.8.26');
INSERT INTO regions (id, name, path) VALUES (68, 'English Garden', '0.1.8.26.68');
INSERT INTO regions (id, name, path) VALUES (69, 'Augustiner Brewery', '0.1.8.26.69');
INSERT INTO regions (id, name, path) VALUES (27, 'Old Town', '0.1.8.27');
INSERT INTO regions (id, name, path) VALUES (70, 'Viktualienmarkt', '0.1.8.27.70');
INSERT INTO regions (id, name, path) VALUES (71, 'Marienplatz', '0.1.8.27.71');
INSERT INTO regions (id, name, path) VALUES (28, 'Munich Airport', '0.1.8.28');
INSERT INTO regions (id, name, path) VALUES (72, 'Terminal 1', '0.1.8.28.72');
INSERT INTO regions (id, name, path) VALUES (73, 'Terminal 2', '0.1.8.28.73');
INSERT INTO regions (id, name, path) VALUES (9, 'Frankfurt', '0.1.9');
INSERT INTO regions (id, name, path) VALUES (29, 'Sachsenhausen', '0.1.9.29');
INSERT INTO regions (id, name, path) VALUES (74, 'Eiserner Steg', '0.1.9.29.74');
INSERT INTO regions (id, name, path) VALUES (75, 'Museum Embankment', '0.1.9.29.75');
INSERT INTO regions (id, name, path) VALUES (30, 'Frankfurt Airport', '0.1.9.30');
INSERT INTO regions (id, name, path) VALUES (76, 'Terminal 1', '0.1.9.30.76');
INSERT INTO regions (id, name, path) VALUES (77, 'Terminal 2', '0.1.9.30.77');
INSERT INTO regions (id, name, path) VALUES (2, 'France', '0.2');
INSERT INTO regions (id, name, path) VALUES (10, 'Paris', '0.2.10');
INSERT INTO regions (id, name, path) VALUES (31, 'Charles de Gaulle Airport', '0.2.10.31');
INSERT INTO regions (id, name, path) VALUES (78, 'Terminal 1', '0.2.10.31.78');
INSERT INTO regions (id, name, path) VALUES (79, 'Terminal 2A', '0.2.10.31.79');
INSERT INTO regions (id, name, path) VALUES (80, 'Terminal 2B', '0.2.10.31.80');
INSERT INTO regions (id, name, path) VALUES (81, 'Terminal 2C', '0.2.10.31.81');
INSERT INTO regions (id, name, path) VALUES (32, 'Orly Airport', '0.2.10.32');
INSERT INTO regions (id, name, path) VALUES (82, 'Terminal South', '0.2.10.32.82');
INSERT INTO regions (id, name, path) VALUES (83, 'Terminal West', '0.2.10.32.83');
INSERT INTO regions (id, name, path) VALUES (33, '1st Arrondissement', '0.2.10.33');
INSERT INTO regions (id, name, path) VALUES (84, 'Louvre', '0.2.10.33.84');
INSERT INTO regions (id, name, path) VALUES (85, 'Palais Royal', '0.2.10.33.85');
INSERT INTO regions (id, name, path) VALUES (34, '7th Arrondissement', '0.2.10.34');
INSERT INTO regions (id, name, path) VALUES (86, 'Eiffel Tower', '0.2.10.34.86');
INSERT INTO regions (id, name, path) VALUES (87, 'Champ de Mars', '0.2.10.34.87');
INSERT INTO regions (id, name, path) VALUES (35, 'Montmartre', '0.2.10.35');
INSERT INTO regions (id, name, path) VALUES (88, 'SacrÃ©-CÅ“ur Basilica', '0.2.10.35.88');
INSERT INTO regions (id, name, path) VALUES (89, 'Place du Tertre', '0.2.10.35.89');
INSERT INTO regions (id, name, path) VALUES (11, 'Nice', '0.2.11');
INSERT INTO regions (id, name, path) VALUES (36, 'Nice CÃ´te d''Azur Airport', '0.2.11.36');
INSERT INTO regions (id, name, path) VALUES (90, 'Terminal 1', '0.2.11.36.90');
INSERT INTO regions (id, name, path) VALUES (91, 'Terminal 2', '0.2.11.36.91');
INSERT INTO regions (id, name, path) VALUES (37, 'Old Town', '0.2.11.37');
INSERT INTO regions (id, name, path) VALUES (92, 'Promenade des Anglais', '0.2.11.37.92');
INSERT INTO regions (id, name, path) VALUES (93, 'Castle Hill', '0.2.11.37.93');
INSERT INTO regions (id, name, path) VALUES (3, 'Italy', '0.3');
INSERT INTO regions (id, name, path) VALUES (12, 'Rome', '0.3.12');
INSERT INTO regions (id, name, path) VALUES (38, 'Leonardo da Vinciâ€“Fiumicino Airport', '0.3.12.38');
INSERT INTO regions (id, name, path) VALUES (94, 'Terminal 1', '0.3.12.38.94');
INSERT INTO regions (id, name, path) VALUES (95, 'Terminal 3', '0.3.12.38.95');
INSERT INTO regions (id, name, path) VALUES (39, 'Centro Storico', '0.3.12.39');
INSERT INTO regions (id, name, path) VALUES (96, 'Colosseum', '0.3.12.39.96');
INSERT INTO regions (id, name, path) VALUES (97, 'Pantheon', '0.3.12.39.97');
INSERT INTO regions (id, name, path) VALUES (13, 'Milan', '0.3.13');
INSERT INTO regions (id, name, path) VALUES (40, 'Malpensa Airport', '0.3.13.40');
INSERT INTO regions (id, name, path) VALUES (98, 'Terminal 1', '0.3.13.40.98');
INSERT INTO regions (id, name, path) VALUES (99, 'Terminal 2', '0.3.13.40.99');
INSERT INTO regions (id, name, path) VALUES (41, 'Linate Airport', '0.3.13.41');
INSERT INTO regions (id, name, path) VALUES (100, 'Terminal 1', '0.3.13.41.100');
INSERT INTO regions (id, name, path) VALUES (42, 'Brera', '0.3.13.42');
INSERT INTO regions (id, name, path) VALUES (101, 'Pinacoteca di Brera', '0.3.13.42.101');
INSERT INTO regions (id, name, path) VALUES (102, 'Brera Botanical Garden', '0.3.13.42.102');
INSERT INTO regions (id, name, path) VALUES (14, 'Venice', '0.3.14');
INSERT INTO regions (id, name, path) VALUES (43, 'Venice Marco Polo Airport', '0.3.14.43');
INSERT INTO regions (id, name, path) VALUES (103, 'Terminal 1', '0.3.14.43.103');
INSERT INTO regions (id, name, path) VALUES (44, 'San Marco', '0.3.14.44');
INSERT INTO regions (id, name, path) VALUES (104, 'St. Mark''s Basilica', '0.3.14.44.104');
INSERT INTO regions (id, name, path) VALUES (105, 'Doge''s Palace', '0.3.14.44.105');
INSERT INTO regions (id, name, path) VALUES (4, 'Portugal', '0.4');
INSERT INTO regions (id, name, path) VALUES (15, 'Lisbon', '0.4.15');
INSERT INTO regions (id, name, path) VALUES (45, 'Lisbon Airport', '0.4.15.45');
INSERT INTO regions (id, name, path) VALUES (106, 'Terminal 1', '0.4.15.45.106');
INSERT INTO regions (id, name, path) VALUES (107, 'Terminal 2', '0.4.15.45.107');
INSERT INTO regions (id, name, path) VALUES (46, 'Alfama', '0.4.15.46');
INSERT INTO regions (id, name, path) VALUES (108, 'SÃ£o Jorge Castle', '0.4.15.46.108');
INSERT INTO regions (id, name, path) VALUES (109, 'Fado Museum', '0.4.15.46.109');
INSERT INTO regions (id, name, path) VALUES (16, 'Porto', '0.4.16');
INSERT INTO regions (id, name, path) VALUES (47, 'Porto Airport', '0.4.16.47');
INSERT INTO regions (id, name, path) VALUES (110, 'Terminal 1', '0.4.16.47.110');
INSERT INTO regions (id, name, path) VALUES (111, 'Terminal 2', '0.4.16.47.111');
INSERT INTO regions (id, name, path) VALUES (48, 'Ribeira', '0.4.16.48');
INSERT INTO regions (id, name, path) VALUES (112, 'Dom LuÃ­s I Bridge', '0.4.16.48.112');
INSERT INTO regions (id, name, path) VALUES (113, 'ClÃ©rigos Tower', '0.4.16.48.113');
INSERT INTO regions (id, name, path) VALUES (5, 'Netherlands', '0.5');
INSERT INTO regions (id, name, path) VALUES (17, 'Amsterdam', '0.5.17');
INSERT INTO regions (id, name, path) VALUES (49, 'Amsterdam Airport Schiphol', '0.5.17.49');
INSERT INTO regions (id, name, path) VALUES (114, 'Terminal 1', '0.5.17.49.114');
INSERT INTO regions (id, name, path) VALUES (115, 'Terminal 2', '0.5.17.49.115');
INSERT INTO regions (id, name, path) VALUES (50, 'Jordaan', '0.5.17.50');
INSERT INTO regions (id, name, path) VALUES (116, 'Anne Frank House', '0.5.17.50.116');
INSERT INTO regions (id, name, path) VALUES (117, 'Westerkerk', '0.5.17.50.117');
INSERT INTO regions (id, name, path) VALUES (18, 'Rotterdam', '0.5.18');
INSERT INTO regions (id, name, path) VALUES (51, 'Rotterdam The Hague Airport', '0.5.18.51');
INSERT INTO regions (id, name, path) VALUES (118, 'Terminal 1', '0.5.18.51.118');
INSERT INTO regions (id, name, path) VALUES (52, 'Delfshaven', '0.5.18.52');
INSERT INTO regions (id, name, path) VALUES (119, 'Delfshaven Harbor', '0.5.18.52.119');
INSERT INTO regions (id, name, path) VALUES (120, 'Pilgrim Fathers Church', '0.5.18.52.120');
INSERT INTO regions (id, name, path) VALUES (6, 'Belgium', '0.6');
INSERT INTO regions (id, name, path) VALUES (19, 'Brussels', '0.6.19');
INSERT INTO regions (id, name, path) VALUES (53, 'Brussels Airport', '0.6.19.53');
INSERT INTO regions (id, name, path) VALUES (121, 'Terminal 1', '0.6.19.53.121');
INSERT INTO regions (id, name, path) VALUES (54, 'Brussels South Charleroi Airport', '0.6.19.54');
INSERT INTO regions (id, name, path) VALUES (122, 'Terminal 1', '0.6.19.54.122');
INSERT INTO regions (id, name, path) VALUES (55, 'European Quarter', '0.6.19.55');
INSERT INTO regions (id, name, path) VALUES (123, 'European Commission', '0.6.19.55.123');
INSERT INTO regions (id, name, path) VALUES (124, 'Parc Leopold', '0.6.19.55.124');
INSERT INTO regions (id, name, path) VALUES (20, 'Antwerp', '0.6.20');
INSERT INTO regions (id, name, path) VALUES (56, 'Antwerp Central Station', '0.6.20.56');
INSERT INTO regions (id, name, path) VALUES (57, 'Grote Markt', '0.6.20.57');

-- Function to get all offers for a region and its children
CREATE OR REPLACE FUNCTION get_offers_for_region(region_id INTEGER)
RETURNS TABLE (offer_id INTEGER) AS $$
BEGIN
    RETURN QUERY
    SELECT o.id
    FROM offers o
    JOIN regions r_sub ON o.subregion_id = r_sub.id
    JOIN regions r_parent ON r_sub.path <@ (
        SELECT path 
        FROM regions 
        WHERE id = region_id
    );
END;
$$ LANGUAGE plpgsql;

-- Function to get full path name for a region
CREATE OR REPLACE FUNCTION get_region_full_path(region_id INTEGER)
RETURNS TEXT AS $$
DECLARE
    result TEXT;
BEGIN
    SELECT string_agg(name, ' > ' ORDER BY nlevel(path))
    INTO result
    FROM regions
    WHERE path @> (SELECT path FROM regions WHERE id = region_id)
    ORDER BY path;
    
    RETURN result;
END;
$$ LANGUAGE plpgsql;
