FROM openjdk:17-alpine

RUN mkdir -p /usr/src/app
COPY . /usr/src/app
WORKDIR /usr/src/app

ADD /target/Taggy-Pet-0.1.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]