import json

def generate_path(node, parent_path=None):
    """Generate ltree path using node IDs"""
    if parent_path:
        return f"{parent_path}.{node['id']}"
    return str(node['id'])

def process_node(node, parent_path=None):
    """Process a node and its subregions recursively"""
    current_path = generate_path(node, parent_path)
    # Generate INSERT statement for current node
    sql = f"INSERT INTO regions (id, name, path) VALUES ({node['id']}, '{node['name'].replace("'", "''")}', '{current_path}');\n"
    
    # Process all subregions recursively
    for subregion in node.get('subregions', []):
        sql += process_node(subregion, current_path)
    
    return sql

def generate_sql_script(json_data):
    """Generate complete SQL script"""
    # Start with setup commands
    sql = """-- Enable ltree extension
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
    id UUID PRIMARY KEY,
    most_specific_region SERIAL NOT NULL REFERENCES Regions(id) ON DELETE CASCADE,
    data VARCHAR(255) NOT NULL,
    start_date BIGINT NOT NULL,
    end_date BIGINT NOT NULL,
    full_days INT NOT NULL,
    number_seats INT NOT NULL,
    price INT NOT NULL,
    car_type VARCHAR(255) NOT NULL,
    has_vollkasko BOOLEAN NOT NULL,
    free_kilometers INT NOT NULL
);

-- Create indexes
CREATE INDEX path_gist_idx ON regions USING GIST (path);
CREATE INDEX path_btree_idx ON regions USING BTREE (path);
CREATE INDEX subregion_idx ON offers(most_specific_region);

-- Insert data
"""
    
    # Process all nodes starting from root
    sql += process_node(json_data)
    
    # Add useful functions
    sql += """
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
"""
    
    return sql

# Your JSON data as a string (this would normally be read from a file)
json_str = '''
{
    "id": 0,
    "name": "European Union",
    "subregions": [
        {
            "id": 1,
            "name": "Germany",
            "subregions": [
                # ... (your full JSON here)
            ]
        }
    ]
}
'''

# Parse JSON and generate SQL
with open("./regions.json", "r") as file:
    data = json.load(file)
sql_script = generate_sql_script(data)

# Write to file
with open('regions_setup.sql', 'w', encoding='utf-8') as f:
    f.write(sql_script)

print("SQL script has been generated in 'regions_setup.sql'")