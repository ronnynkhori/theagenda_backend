FROM maven:3.8.4-openjdk-17

ADD theagenda.jar theagenda.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/theagenda.jar"]
