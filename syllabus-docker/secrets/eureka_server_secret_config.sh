#!/bin/sh
openssl enc -aes-256-cbc -d -pbkdf2 -in /run/secrets/eureka_server_secret -out password -pass pass:800Tj9DDM#@K
export EUREKA_SERVER_PASSWORD=$(cat password)
rm password
exec "$@"
