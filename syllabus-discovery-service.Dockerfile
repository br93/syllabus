FROM maven:3.9-amazoncorretto-17-debian AS maven_build
COPY pom.xml /tmp/
COPY syllabus-discovery-service/pom.xml /tmp/app/
COPY syllabus-discovery-service/src /tmp/app/src

COPY syllabus-secrets/eureka_server_secret_config.sh /tmp/app/

WORKDIR /tmp/app/

RUN mvn package

FROM amazoncorretto:17-alpine3.16-jdk
COPY --from=maven_build /tmp/app/target/syllabus-discovery-service-0.0.1-SNAPSHOT.jar /data/eureka.jar

RUN apk upgrade --update-cache --available && \
    apk add openssl && \
    rm -rf /var/cache/apk/*
COPY --from=maven_build /tmp/app/eureka_server_secret_config.sh /data/secret_config.sh
RUN chmod +x /data/secret_config.sh

EXPOSE 8761

ENTRYPOINT ["/data/secret_config.sh"]
CMD ["java", "-jar", "/data/eureka.jar"]