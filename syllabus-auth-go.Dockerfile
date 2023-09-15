#BUILD
FROM golang:1.21.0-alpine3.18 AS build
WORKDIR /app

COPY syllabus-auth-go/ ./
COPY syllabus-auth-go/.env-docker ./.env
RUN go mod download

RUN go build -o auth-go .
CMD ["./auth-go"]