FROM maven:3.9.4-amazoncorretto-17-al2023 AS maven_build
COPY pom.xml /tmp/
COPY syllabus-account-management/pom.xml /tmp/app/
COPY syllabus-account-management/src /tmp/app/src

COPY syllabus-secrets/eureka_server_postgres_secret_config.sh /tmp/app/

WORKDIR /tmp/app/

RUN mvn package

FROM amazoncorretto:17.0.8-alpine3.17
COPY --from=maven_build /tmp/app/target/syllabus-account-management-0.0.1-SNAPSHOT.jar /data/account.jar

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
COPY --from=maven_build /tmp/app/eureka_server_postgres_secret_config.sh /data/secret_config.sh
RUN chmod +x /data/secret_config.sh

ENTRYPOINT ["/data/secret_config.sh"]
CMD ["java", "-jar", "/data/account.jar"]