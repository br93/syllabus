FROM maven:3.9.4-amazoncorretto-17-al2023 AS maven_build
COPY pom.xml /tmp/
COPY syllabus-students/pom.xml /tmp/app/
COPY syllabus-students/src /tmp/app/src

COPY syllabus-secrets/eureka_server_secret_config.sh /tmp/app/

WORKDIR /tmp/app/

RUN mvn clean package

FROM amazoncorretto:17.0.8-alpine3.17
COPY --from=maven_build /tmp/app/target/syllabus-students-0.0.1-SNAPSHOT.jar /data/students.jar

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
COPY --from=maven_build /tmp/app/eureka_server_secret_config.sh /data/secret_config.sh
RUN chmod +x /data/secret_config.sh

ENTRYPOINT ["/data/secret_config.sh"]
CMD ["java", "-jar", "/data/students.jar"]