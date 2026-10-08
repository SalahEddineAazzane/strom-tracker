CREATE TABLE strompreise (
                             id BIGSERIAL PRIMARY KEY,
                             start_zeit TIMESTAMPTZ NOT NULL,
                             end_zeit TIMESTAMPTZ NOT NULL,
                             preis_eur_mwh NUMERIC(10,2) NOT NULL
);