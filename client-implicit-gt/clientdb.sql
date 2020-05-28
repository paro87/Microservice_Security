CREATE DATABASE clientdb;
use clientdb;
create table client_user(
                            id bigint auto_increment primary key,
                            username varchar(100),
                            password varchar(100),
                            access_token varchar(100) NULL,
                            access_token_validity datetime NULL,
                            refresh_token varchar(100) NULL
);
insert into client_user (username, password) value ('client', '$2a$04$z1j6djoZZWtspnq2Lhtv9OcYL7Q9WoiQHUcw3pEWLrXX3RfBuMe7C');