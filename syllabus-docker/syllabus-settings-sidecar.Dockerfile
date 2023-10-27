FROM eclipse-temurin:11-jre-alpine
COPY app/app-settings-sidecar.jar app/settings.jar
COPY syllabus-docker/secrets/eureka_server_secret_config.sh /tmp/secret_config.sh

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
RUN chmod +x /tmp/secret_config.sh

ENTRYPOINT ["/tmp/secret_config.sh"]
CMD ["java", "-jar", "/app/settings.jar"]