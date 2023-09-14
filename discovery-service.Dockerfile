FROM maven:3.9-amazoncorretto-17-debian AS maven_build
COPY pom.xml /tmp/
COPY syllabus-discovery-service/pom.xml /tmp/app/
COPY syllabus-discovery-service/src /tmp/app/src

WORKDIR /tmp/app/

RUN mvn package

FROM amazoncorretto:17-alpine3.16-jdk
COPY --from=maven_build /tmp/app/target/syllabus-discovery-service-0.0.1-SNAPSHOT.jar /data/eureka.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/data/eureka.jar"]