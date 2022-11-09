create database user_data;

use user_data;

create table users(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	user_id UUID unique not null default gen_random_uuid(),
	username text unique not null,
 	email text unique not null,
 	first_name text not null,
 	last_name text not null,
	created_at timestamptz not null default now()
);