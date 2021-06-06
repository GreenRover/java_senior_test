FROM openjdk:11
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "webserver.jar"]