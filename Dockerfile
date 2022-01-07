FROM gradle:jdk8 AS builder
WORKDIR /home
COPY build.gradle settings.gradle data-analyser/
COPY src data-analyser/src/
RUN cd data-analyser && gradle build

FROM openjdk:8-jre-alpine
RUN adduser --disabled-password analyser
USER analyser
WORKDIR /home/analyser
COPY --from=builder /home/data-analyser/build/libs/data-analysis-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "data-analysis-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080