#!/usr/bin/env bash

# Create keyspace and tables
echo "Creating keyspace and tables..."
cqlsh cassandra -u cassandra -p cassandra -e "CREATE KEYSPACE IF NOT EXISTS lol WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};"
#CREATE TABLE IF NOT EXISTS lol.t_currency (
#id uuid PRIMARY KEY, close double, datetime text, name text, previousclose double, symbol text);
#ALL GOOD