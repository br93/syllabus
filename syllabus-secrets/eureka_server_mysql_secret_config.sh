#!/bin/sh
openssl enc -aes-256-cbc -d -pbkdf2 -in /run/secrets/eureka_server_secret -out eureka_password -pass pass:800Tj9DDM#@K
openssl enc -aes-256-cbc -d -pbkdf2 -in /run/secrets/mysql_secret -out mysql_password -pass pass:800Tj9DDM#@K

export EUREKA_SERVER_PASSWORD=$(cat eureka_password)
export MYSQL_PASSWORD=$(cat mysql_password)

rm eureka_password
rm mysql_password

exec "$@"
