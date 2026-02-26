CREATE TABLE stockprice
(
    id         BIGINT NOT NULL,
    stock_id   BIGINT,
    stockprice BIGINT NOT NULL,
    modified   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_stockprice PRIMARY KEY (id)
);

ALTER TABLE stockprice
    ADD CONSTRAINT FK_STOCKPRICE_ON_STOCK FOREIGN KEY (stock_id) REFERENCES stock (stock_id);