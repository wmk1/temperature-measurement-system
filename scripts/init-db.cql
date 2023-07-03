CREATE KEYSPACE IF NOT EXISTS kontakt_keyspace
WITH REPLICATION = {
 'class' : 'SimpleStrategy',
 'replication_factor' : 1
};

USE kontakt_keyspace;

CREATE TABLE IF NOT EXISTS measurements (
    id UUID PRIMARY KEY,
    temperature double,
    timestamp bigint,
    thermometerId bigint,
    roomId bigint
);