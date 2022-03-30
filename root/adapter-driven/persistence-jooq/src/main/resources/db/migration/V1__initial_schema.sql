create table messages
(
    id   uuid primary key,
    body jsonb not null
);

create index ix_messages on messages using gin (body jsonb_path_ops);
