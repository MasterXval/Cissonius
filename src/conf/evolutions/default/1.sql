# --- First database schema

# --- !Ups

CREATE TABLE product(
  id                        SERIAL PRIMARY KEY,
  name                      VARCHAR(255) NOT NULL ,
  description               VARCHAR(255),
  price                     INTEGER,
  price_strikeout           INTEGER,
  image                     VARCHAR(255)
);

# --- !Downs

DROP TABLE IF EXISTS product;