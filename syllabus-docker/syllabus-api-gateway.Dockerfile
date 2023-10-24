FROM maven:3.9-amazoncorretto-17-debian AS maven_build
COPY pom.xml /tmp/
COPY syllabus-api-gateway/pom.xml /tmp/app/
COPY syllabus-api-gateway/src /tmp/app/src

COPY syllabus-docker/secrets/eureka_server_secret_config.sh /tmp/app/

WORKDIR /tmp/app/

RUN mvn package

FROM amazoncorretto:17-alpine3.16-jdk
COPY --from=maven_build /tmp/app/target/syllabus-api-gateway-0.0.1-SNAPSHOT.jar /data/gateway.jar

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
COPY --from=maven_build /tmp/app/eureka_server_secret_config.sh /data/secret_config.sh
RUN chmod +x /data/secret_config.sh

EXPOSE 8765

ENTRYPOINT ["/data/secret_config.sh"]
CMD ["java", "-jar", "/data/gateway.jar"]