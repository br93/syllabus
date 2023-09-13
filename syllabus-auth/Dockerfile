FROM maven:3.9.4-amazoncorretto-11-al2023 AS maven_build
COPY pom.xml /tmp/
COPY src /tmp/src
WORKDIR /tmp/

RUN mvn package spring-boot:repackage

FROM amazoncorretto:11-al2023
COPY --from=maven_build /tmp/target/syllabus-auth-0.0.1-SNAPSHOT.jar /data/auth.jar

ENTRYPOINT ["java", "-jar", "/data/auth.jar"]