CREATE DATABASE authorizationdb;
create table oauth_client_details (
                                      client_id VARCHAR(256) PRIMARY KEY,
                                      resource_ids VARCHAR(256),
                                      client_secret VARCHAR(256),
                                      scope VARCHAR(256),
                                      authorized_grant_types VARCHAR(256),
                                      web_server_redirect_uri VARCHAR(256),
                                      authorities VARCHAR(256),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(256)
);

create table oauth_access_token (
                                    token_id VARCHAR(256),
                                    token LONG VARBINARY,
                                    authentication_id VARCHAR(256) PRIMARY KEY,
                                    user_name VARCHAR(256),
                                    client_id VARCHAR(256),
                                    authentication LONG VARBINARY,
                                    refresh_token VARCHAR(256)
);

create table oauth_approvals (
                                 userId VARCHAR(256),
                                 clientId VARCHAR(256),
                                 scope VARCHAR(256),
                                 status VARCHAR(10),
                                 expiresAt TIMESTAMP,
                                 lastModifiedAt TIMESTAMP
);
create table oauth_refresh_token (
                                     token_id VARCHAR(256),
                                     token LONG VARBINARY,
                                     authentication LONG VARBINARY
);

INSERT INTO oauth_client_details
    (client_id, resource_ids, client_secret, scope, authorized_grant_types,  web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
       ('clientAuthorizationCodeGT', null, '123456', 'read_data', 'authorization_code', 'http://localhost:9001/callback', null, 3000, -1, null, 'false'),
       ('clientImplicitGT', null, '123456', 'read_data', 'implicit', 'http://localhost:9002/callback', null, 3000, -1, null, 'false'),
       ('clientPasswordGT', null, '123456', 'read_data', 'password', 'http://localhost:9003/callback', null, 3000, -1, null, 'false'),
       ('clientClientCredentialsGT', null, '123456', 'read_data', 'client_credentials', null, null, 3000, -1, null, 'false');

UPDATE oauth_client_details
SET client_secret = '$2a$04$z1j6djoZZWtspnq2Lhtv9OcYL7Q9WoiQHUcw3pEWLrXX3RfBuMe7C'
WHERE client_id = 'clientAuthorizationCodeGT';

UPDATE oauth_client_details
SET client_secret = '$2a$04$z1j6djoZZWtspnq2Lhtv9OcYL7Q9WoiQHUcw3pEWLrXX3RfBuMe7C'
WHERE client_id = 'clientImplicitGT';

UPDATE oauth_client_details
SET client_secret = '$2a$04$z1j6djoZZWtspnq2Lhtv9OcYL7Q9WoiQHUcw3pEWLrXX3RfBuMe7C'
WHERE client_id = 'clientPasswordGT';

UPDATE oauth_client_details
SET client_secret = '$2a$04$z1j6djoZZWtspnq2Lhtv9OcYL7Q9WoiQHUcw3pEWLrXX3RfBuMe7C'
WHERE client_id = 'clientClientCredentialsGT';