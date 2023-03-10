-- Table: public.categories

DROP TABLE IF EXISTS public.categories;
CREATE TABLE IF NOT EXISTS public.categories
(
    id bigint NOT NULL DEFAULT nextval('categories_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (id),
    CONSTRAINT uk_t8o6pivur7nn124jehx7cygw5 UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categories
    OWNER to perfectoz;

-- Table: public.users
DROP TABLE IF EXISTS public.users;
CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to perfectoz;

-- Table: public.roles
DROP TABLE IF EXISTS public.roles;
CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT uk_ofx66keruapi6vyqpv6f2or37 UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.roles
    OWNER to perfectoz;

-- Table: public.posts
DROP TABLE IF EXISTS public.posts;
CREATE TABLE IF NOT EXISTS public.posts
(
    id bigint NOT NULL DEFAULT nextval('posts_id_seq'::regclass),
    content character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    category_id bigint,
    user_id bigint,
    CONSTRAINT posts_pkey PRIMARY KEY (id),
    CONSTRAINT ukmchce1gm7f6otpphxd6ixsdps UNIQUE (title),
    CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkijnwr3brs8vaosl80jg9rp7uc FOREIGN KEY (category_id)
        REFERENCES public.categories (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.posts
    OWNER to perfectoz;

-- Table: public.comments
DROP TABLE IF EXISTS public.comments;
CREATE TABLE IF NOT EXISTS public.comments
(
    id bigint NOT NULL DEFAULT nextval('comments_id_seq'::regclass),
    body character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    post_id bigint,
    CONSTRAINT comments_pkey PRIMARY KEY (id),
    CONSTRAINT fkh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id)
        REFERENCES public.posts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.comments
    OWNER to perfectoz;


-- Table: public.users_roles
DROP TABLE IF EXISTS public.users_roles;
CREATE TABLE IF NOT EXISTS public.users_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users_roles
    OWNER to perfectoz;
