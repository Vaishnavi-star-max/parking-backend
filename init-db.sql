-- Initialize parking database with required extensions and basic setup

-- Create extensions if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create indexes for better performance (these will be created by JPA as well, but good to have)
-- Note: Tables will be created by Spring Boot JPA, this is just for additional setup

-- You can add any additional database initialization here
-- For example, inserting default data, creating custom functions, etc.

-- Example: Create a function to generate vehicle numbers for testing
CREATE OR REPLACE FUNCTION generate_test_vehicle_number()
RETURNS TEXT AS $$
BEGIN
    RETURN 'KA' || LPAD((RANDOM() * 99)::INTEGER::TEXT, 2, '0') || 
           CHR(65 + (RANDOM() * 25)::INTEGER) || CHR(65 + (RANDOM() * 25)::INTEGER) ||
           LPAD((RANDOM() * 9999)::INTEGER::TEXT, 4, '0');
END;
$$ LANGUAGE plpgsql;