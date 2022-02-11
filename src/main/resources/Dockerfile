FROM openjdk:8-jre-alpine
ADD /@project.build.finalName@.jar //
ADD / /

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/@project.build.finalName@.jar", "--server.port=8080"]