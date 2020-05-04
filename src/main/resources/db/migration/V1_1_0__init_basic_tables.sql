create table app
(
    app_id varchar(255) not null primary key,
    app_info varchar(255),
    role varchar(255)
);
create table message
(
    id serial not null primary key,
    subject varchar(255),
    content text,
    done boolean
);

