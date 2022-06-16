CREATE TABLE public.address (
    id bigint NOT NULL,
    district character varying(100) NOT NULL,
    number integer,
    street character varying(100) NOT NULL
);


ALTER TABLE public.address OWNER TO postgres;

ALTER TABLE public.address ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.expense_control (
    id bigint NOT NULL,
    date_expense date,
    description character varying(255),
    expense character varying(255),
    value numeric(10,2),
    person_id bigint
);


ALTER TABLE public.expense_control OWNER TO postgres;

ALTER TABLE public.expense_control ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.expense_control_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.person (
    id bigint NOT NULL,
    birth_date date,
    email character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    address_id bigint,
    gender character varying(255)
);


ALTER TABLE public.person OWNER TO postgres;

INSERT INTO public.address VALUES (10, 'District Teste yyyy', 56, 'Street Test 14');
INSERT INTO public.address VALUES (9, 'District Teste hhh', 8877, 'Street Test Change');
INSERT INTO public.address VALUES (8, 'District Teste ', 34, 'Street Teste ');
INSERT INTO public.address VALUES (1, 'District Teste', 5000, 'Street Teste');
INSERT INTO public.address VALUES (14, 'District Teste ', 1234, 'teste 123');


INSERT INTO public.expense_control VALUES (1, '2022-03-03', 'Testando Comprar', 'EXPENSE', 234.00, 2);
INSERT INTO public.expense_control VALUES (2, '2022-03-11', 'Testando Comprar 2', 'REVENUE', 65.00, 2);
INSERT INTO public.expense_control VALUES (3, '2022-03-24', 'Testando Comprar 3', 'EXPENSE', 345.00, 2);
INSERT INTO public.expense_control VALUES (4, '2022-03-02', 'Testando Comprar', 'REVENUE', 32.00, 8);
INSERT INTO public.expense_control VALUES (5, '2022-04-08', 'Testando Comprar 3', 'EXPENSE', 658.00, 8);
INSERT INTO public.expense_control VALUES (6, '2022-04-08', 'Testando Comprar', 'REVENUE', 345.00, 9);
INSERT INTO public.expense_control VALUES (7, '2022-04-29', 'Testando Comprar', 'REVENUE', 348.00, 10);


INSERT INTO public.person VALUES (2, '1997-09-23', 'marian@gmail.com', 'Marian Lemans', 1, 'FEMALE');
INSERT INTO public.person VALUES (10, '2022-02-07', 'robbin@gmail.com', 'Robbin William', 10, 'MALE');
INSERT INTO public.person VALUES (8, '2022-02-16', 'martina@gmail.com', 'Martina Tina', 8, 'FEMALE');
INSERT INTO public.person VALUES (9, '2022-02-11', 'denis@gmail.com', 'Denis Rodman', 9, 'MALE');
INSERT INTO public.person VALUES (14, '2022-04-06', 'teste@gmail.com', 'teste', 14, 'MALE');

SELECT pg_catalog.setval('public.address_id_seq', 14, true);

SELECT pg_catalog.setval('public.expense_control_id_seq', 7, true);

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.expense_control
    ADD CONSTRAINT expense_control_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkk7rgn6djxsv2j2bv1mvuxd4m9 FOREIGN KEY (address_id) REFERENCES public.address(id);

ALTER TABLE ONLY public.expense_control
    ADD CONSTRAINT fkkkweuxd575q34l264sb29lprb FOREIGN KEY (person_id) REFERENCES public.person(id);


