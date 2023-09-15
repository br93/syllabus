#BUILD
FROM golang:1.21.0-alpine3.18 AS build
WORKDIR /app

COPY syllabus-settings-go/ ./
COPY syllabus-settings-go/.env ./
RUN go mod download

RUN go build -o settings-go .
CMD ["./settings-go"]