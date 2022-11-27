create table user_event_log(
	id UUID not null default gen_random_uuid(),
    user_id bigint,
    user_uuid UUID not null,
    created_at TIMESTAMPTZ  NOT null,
    process_start_time TIMESTAMPTZ  NOT null,
    process_end_time TIMESTAMPTZ  NOT null,
    process_time bigint,
    user_event text not null
    exception_message text
);

SELECT create_hypertable('user_event_log', 'created_at');