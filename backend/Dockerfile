#FROM openjdk:17
#COPY ./target/backend-0.0.1-SNAPSHOT.jar app.jar
#COPY ./config ./config
#ENTRYPOINT ["java", "-jar", "app.jar"]
#EXPOSE 3081

FROM openjdk:17
ADD /target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 3081