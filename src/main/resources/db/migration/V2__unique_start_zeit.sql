ALTER TABLE strompreise ADD CONSTRAINT uq_strompreise_start UNIQUE (start_zeit);
CREATE INDEX idx_strompreise_start ON strompreise (start_zeit);