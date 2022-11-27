create database user_data;

use user_data;

CREATE TYPE status as enum (
	'ACTIVE', 'INACTIVE', 'DELETED'
);

create table user_info(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	user_id UUID NOT null,
	username text unique ,
 	email text unique ,
 	first_name text ,
 	last_name text ,
	created_at timestamptz not null default now(),
	status status default 'ACTIVE'
);

SELECT create_hypertable('user_event_log', 'created_at');