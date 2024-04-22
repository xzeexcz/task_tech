create table t_payments
(
    account_from       numeric(38, 2),
    account_to         numeric(38, 2),
    limit_exceded      boolean,
    sum                double precision,
    date_time          timestamp(6) with time zone,
    id                 uuid not null
        primary key,
    currency_shortname varchar(255)
        constraint t_payments_currency_shortname_check
            check ((currency_shortname)::text = ANY
                   ((ARRAY ['KZT'::character varying, 'RUB'::character varying, 'USD'::character varying])::text[])),
    expense_category   varchar(255)
        constraint t_payments_expense_category_check
            check ((expense_category)::text = ANY
                   ((ARRAY ['PRODUCT'::character varying, 'SERVICE'::character varying])::text[]))
);

alter table t_payments
    owner to myuser;

create table t_limits
(
    account                  numeric(38, 2),
    limit_balance            double precision,
    limit_sum                double precision,
    limit_datetime           timestamp(6) with time zone,
    id                       uuid not null
        primary key,
    expense_category         varchar(255)
        constraint t_limits_expense_category_check
            check ((expense_category)::text = ANY
                   ((ARRAY ['PRODUCT'::character varying, 'SERVICE'::character varying])::text[])),
    limit_currency_shortname varchar(255)
        constraint t_limits_limit_currency_shortname_check
            check ((limit_currency_shortname)::text = ANY
                   ((ARRAY ['KZT'::character varying, 'RUB'::character varying, 'USD'::character varying])::text[]))
);

alter table t_limits
    owner to myuser;

