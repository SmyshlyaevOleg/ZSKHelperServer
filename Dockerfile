FROM openjdk:17
ADD /target/ZSKHelperServer-0.0.1-SNAPSHOT.jar botserver.jar
ENTRYPOINT ["java", "-jar", "botserver.jar"]
