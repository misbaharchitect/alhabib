CREATE SEQUENCE public.student_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.student (
                                id bigint NOT NULL,
                                name varchar NULL,
                                age int NULL,
                                dob date NULL,
                                grade int NULL,
                                CONSTRAINT student_pk PRIMARY KEY (id)
);
ALTER SEQUENCE public.student_seq OWNED BY public.student.id;

CREATE SEQUENCE public.employees_data_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.employees_data (
                                id bigint NOT NULL,
                                name varchar NULL,
                                age int NULL,
                                type varchar NULL,
                                doj date NULL,
                                CONSTRAINT employee_pk PRIMARY KEY (id)
);

ALTER SEQUENCE public.employees_data_seq OWNED BY public.employees_data.id;