CREATE KEYSPACE IF NOT EXISTS adik
  WITH REPLICATION = {
    'class' : 'SimpleStrategy',
    'replication_factor' : 1
  };
-- create table t_currency
-- (
--     id            uuid primary key,
--     close         double,
--     datetime      text,
--     name          text,
--     previousclose double,
--     symbol        text
-- );