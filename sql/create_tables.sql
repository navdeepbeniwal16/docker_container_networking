-- Table: public.Person

-- DROP TABLE IF EXISTS public."Person";

CREATE TABLE IF NOT EXISTS public."Person"
(
    firstname character varying COLLATE pg_catalog."default",
    lastname character varying COLLATE pg_catalog."default"
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Person"
    OWNER to postgres;