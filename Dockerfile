FROM amazoncorretto:17-alpine3.18
WORKDIR /opt/social_net_otus
COPY /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
