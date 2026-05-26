-- Car Rental database schema, matching the Vertabelo ER diagram.
-- Spring Boot runs this on startup (spring.sql.init.mode=always +
-- spring.jpa.defer-datasource-initialization=true). All statements use
-- IF NOT EXISTS so re-running is safe and so the 4 tables that overlap
-- existing JPA entities (country, city, street, building_no) keep the
-- shape Hibernate created from those entities.

-- ---------- root tables (no FKs) ----------

CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS model (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transmission_type (
    id   SERIAL PRIMARY KEY,
    type VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS fuel_type (
    id   SERIAL PRIMARY KEY,
    type VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS discount (
    id            SERIAL PRIMARY KEY,
    discount_rate INTEGER NOT NULL,
    start_date    DATE    NOT NULL,
    end_date      DATE    NOT NULL
);

CREATE TABLE IF NOT EXISTS building_no (
    id SERIAL PRIMARY KEY,
    no INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS credit_card (
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    card_number     VARCHAR(16) NOT NULL,
    end_date        DATE        NOT NULL,
    security_number VARCHAR(4)  NOT NULL
);

-- ---------- geographic hierarchy (diagram order: building_no <- street <- city <- country) ----------

CREATE TABLE IF NOT EXISTS street (
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    building_no_id INTEGER     NOT NULL REFERENCES building_no (id)
);

CREATE TABLE IF NOT EXISTS city (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    street_id INTEGER     NOT NULL REFERENCES street (id)
);

CREATE TABLE IF NOT EXISTS country (
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    city_id INTEGER     NOT NULL REFERENCES city (id)
);

-- ---------- pricing ----------

CREATE TABLE IF NOT EXISTS price (
    id                  SERIAL PRIMARY KEY,
    price               INTEGER NOT NULL,
    discount_id         INTEGER REFERENCES discount (id),
    avaliable_discount  BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS car_brand (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    model_id INTEGER REFERENCES model (id)
);

-- ---------- address tables (one per owning entity, all FK -> country) ----------

CREATE TABLE IF NOT EXISTS employee_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS customer_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS car_rental_company_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS car_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS start_location_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS end_location_address (
    id         SERIAL PRIMARY KEY,
    country_id INTEGER REFERENCES country (id)
);

-- ---------- people: employees, customer, car_rental_company ----------

CREATE TABLE IF NOT EXISTS employees (
    id                             SERIAL PRIMARY KEY,
    employee_number                INTEGER     NOT NULL,
    first_name                     VARCHAR(50) NOT NULL,
    last_name                      VARCHAR(50) NOT NULL,
    national_identification_number VARCHAR(20) NOT NULL,
    phone_number                   VARCHAR(50) NOT NULL,
    users_id                       INTEGER REFERENCES users (id),
    employee_address_id            INTEGER REFERENCES employee_address (id)
);

CREATE TABLE IF NOT EXISTS customer (
    id                             SERIAL PRIMARY KEY,
    users_id                       INTEGER REFERENCES users (id),
    customer_address_id            INTEGER REFERENCES customer_address (id),
    credit_card_id                 INTEGER REFERENCES credit_card (id),
    first_name                     VARCHAR(50) NOT NULL,
    last_name                      VARCHAR(50) NOT NULL,
    date_of_birth                  DATE        NOT NULL,
    phone_number                   VARCHAR(50) NOT NULL,
    national_identification_number VARCHAR(20) NOT NULL,
    phone_verification             BOOLEAN     NOT NULL,
    email_verification             BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS car_rental_company (
    id                             SERIAL PRIMARY KEY,
    users_id                       INTEGER REFERENCES users (id),
    employees_id                   INTEGER REFERENCES employees (id),
    car_rental_company_address_id  INTEGER REFERENCES car_rental_company_address (id),
    company_number                 INTEGER      NOT NULL,
    company_name                   VARCHAR(50)  NOT NULL,
    phone_number                   VARCHAR(50)  NOT NULL,
    tax_number                     VARCHAR(50)  NOT NULL,
    bank_account_number            VARCHAR(100) NOT NULL,
    address                        INTEGER      NOT NULL,
    phone_verification             BOOLEAN      NOT NULL,
    email_verification             BOOLEAN      NOT NULL,
    employee_verification          BOOLEAN      NOT NULL
);

-- ---------- cars and dependents ----------

CREATE TABLE IF NOT EXISTS cars (
    id                    SERIAL PRIMARY KEY,
    is_avaliable          BOOLEAN NOT NULL,
    car_rental_company_id INTEGER REFERENCES car_rental_company (id),
    car_brand_id          INTEGER REFERENCES car_brand (id),
    transmission_type_id  INTEGER REFERENCES transmission_type (id),
    price_id              INTEGER REFERENCES price (id),
    fuel_type_id          INTEGER REFERENCES fuel_type (id),
    car_address_id        INTEGER REFERENCES car_address (id),
    employees_id          INTEGER REFERENCES employees (id)
);

CREATE TABLE IF NOT EXISTS images (
    id        SERIAL PRIMARY KEY,
    image_url VARCHAR(100) NOT NULL,
    cars_id   INTEGER REFERENCES cars (id),
    users_id  INTEGER REFERENCES users (id)
);

-- ---------- reservation flow: locations, payment, commission, reservation, invoice ----------

CREATE TABLE IF NOT EXISTS start_location (
    id                        SERIAL PRIMARY KEY,
    start_location_address_id INTEGER REFERENCES start_location_address (id)
);

CREATE TABLE IF NOT EXISTS end_location (
    id                      SERIAL PRIMARY KEY,
    end_location_address_id INTEGER REFERENCES end_location_address (id)
);

CREATE TABLE IF NOT EXISTS payment (
    id             SERIAL PRIMARY KEY,
    customer_id    INTEGER REFERENCES customer (id),
    is_paid        BOOLEAN NOT NULL,
    credit_card_id INTEGER REFERENCES credit_card (id),
    cars_id        INTEGER REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS commission (
    id              SERIAL PRIMARY KEY,
    payment_id      INTEGER REFERENCES payment (id),
    commission_rate INTEGER NOT NULL,
    start_date      DATE    NOT NULL,
    end_date        DATE    NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    id                    SERIAL PRIMARY KEY,
    start_date            DATE NOT NULL,
    end_date              DATE NOT NULL,
    cars_id               INTEGER REFERENCES cars (id),
    customer_id           INTEGER REFERENCES customer (id),
    start_location_id     INTEGER REFERENCES start_location (id),
    end_location_id       INTEGER REFERENCES end_location (id),
    payment_id            INTEGER REFERENCES payment (id),
    car_rental_company_id INTEGER REFERENCES car_rental_company (id),
    commission_id         INTEGER REFERENCES commission (id)
);

CREATE TABLE IF NOT EXISTS invoice (
    id             SERIAL PRIMARY KEY,
    invoice_date   DATE    NOT NULL,
    payment_status BOOLEAN NOT NULL,
    payment_id     INTEGER REFERENCES payment (id),
    reservation_id INTEGER REFERENCES reservation (id),
    commission_id  INTEGER REFERENCES commission (id)
);
