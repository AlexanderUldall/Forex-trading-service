CREATE TABLE forex_cutoff (
    CURRENCY_CODE   VARCHAR(3)      NOT NULL UNIQUE,
    country VARCHAR(128) NOT NULL,
    today_time TIME NOT NULL,
    tomorrow_time TIME NOT NULL,
    after_tomorrow_time TIME NOT NULL,
    PRIMARY KEY (CURRENCY_CODE)
);