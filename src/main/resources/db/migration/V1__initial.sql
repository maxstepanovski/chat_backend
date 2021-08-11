DROP TABLE IF EXISTS public."user";
CREATE TABLE public."user"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 1000000000 CACHE 1 ),
    user_password character varying COLLATE pg_catalog."default" NOT NULL,
    user_name character varying COLLATE pg_catalog."default" NOT NULL,
    enabled boolean,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to postgres;


DROP INDEX IF EXISTS user_name_index;
CREATE UNIQUE INDEX user_name_index
    ON public."user" USING btree
        (user_name COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;


DROP TABLE IF EXISTS public.authority;
CREATE TABLE public.authority
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 1000000000 CACHE 1 ),
    user_id bigint NOT NULL,
    authority character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authority_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.authority
    OWNER to postgres;


DROP TABLE IF EXISTS public.message;
CREATE TABLE public.message
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 10000000000 CACHE 1 ),
    message_text text COLLATE pg_catalog."default" NOT NULL,
    message_time bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT message_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.message
    OWNER to postgres;


DROP TABLE IF EXISTS public.conversation;
CREATE TABLE public.conversation
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 10000000000 CACHE 1 ),
    conversation_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT conversation_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.conversation
    OWNER to postgres;


DROP TABLE IF EXISTS public.conversation_message;
CREATE TABLE public.conversation_message
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 10000000000 CACHE 1 ),
    conversation_id bigint NOT NULL,
    message_id bigint NOT NULL,
    CONSTRAINT conversation_message_pkey PRIMARY KEY (id),
    CONSTRAINT fk_conversation_id FOREIGN KEY (conversation_id)
        REFERENCES public.conversation (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_message_id FOREIGN KEY (message_id)
        REFERENCES public.message (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.conversation_message
    OWNER to postgres;


DROP TABLE IF EXISTS public.user_conversation;
CREATE TABLE public.user_conversation
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 10000000000 CACHE 1 ),
    user_id bigint NOT NULL,
    conversation_id bigint NOT NULL,
    CONSTRAINT user_conversation_pkey PRIMARY KEY (id),
    CONSTRAINT fk_conversation_id FOREIGN KEY (conversation_id)
        REFERENCES public.conversation (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.user_conversation
    OWNER to postgres;


DROP TABLE IF EXISTS public.user_firebase_token;
CREATE TABLE public.user_firebase_token
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 1000000000 CACHE 1 ),
    user_id bigint NOT NULL,
    firebase_token character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.user_firebase_token
    OWNER to postgres;

DROP INDEX IF EXISTS public.user_id_index;
CREATE INDEX user_id_index
    ON public.user_firebase_token USING btree
        (user_id ASC NULLS LAST)
    TABLESPACE pg_default;