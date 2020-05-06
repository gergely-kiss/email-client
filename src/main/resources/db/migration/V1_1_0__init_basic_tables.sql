create table app
(
    app_id varchar(255) not null primary key,
    app_info varchar(255),
    role varchar(255)
);
create table message
(
    id integer not null primary key,
    difficulty varchar(255),
    content text,
    done boolean
);
create table process
(
    id varchar (255) not null primary key ,
    is_running boolean not null
);
