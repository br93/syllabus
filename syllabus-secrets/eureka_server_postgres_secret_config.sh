#!/bin/sh
openssl enc -aes-256-cbc -d -pbkdf2 -in /run/secrets/eureka_server_secret -out eureka_password -pass pass:800Tj9DDM#@K
openssl enc -aes-256-cbc -d -pbkdf2 -in /run/secrets/postgres_secret -out postgres_password -pass pass:800Tj9DDM#@K

export EUREKA_SERVER_PASSWORD=$(cat eureka_password)
export POSTGRES_PASSWORD=$(cat postgres_password)

rm eureka_password
rm postgres_password

exec "$@"
