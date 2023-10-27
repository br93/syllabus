FROM alpine:3.18.4

COPY app/app-settings /app/settings
CMD ["/app/settings"]