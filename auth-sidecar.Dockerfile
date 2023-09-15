FROM maven:3.9.4-amazoncorretto-11-al2023 AS maven_build
COPY syllabus-auth/pom.xml /tmp/app/
COPY syllabus-auth/src /tmp/app/src

COPY syllabus-secrets/eureka_server_secret_config.sh /tmp/app/

WORKDIR /tmp/app

RUN mvn package

FROM amazoncorretto:11.0.20-alpine3.18
COPY --from=maven_build /tmp/app/target/syllabus-auth-0.0.1-SNAPSHOT.jar /data/auth.jar

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
COPY --from=maven_build /tmp/app/eureka_server_secret_config.sh /data/secret_config.sh
RUN chmod +x /data/secret_config.sh

ENTRYPOINT ["/data/secret_config.sh"]
CMD ["java", "-jar", "/data/auth.jar"]