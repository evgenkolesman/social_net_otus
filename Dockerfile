FROM amazoncorretto:17-alpine3.18
WORKDIR /opt/socialnetotus
COPY /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
