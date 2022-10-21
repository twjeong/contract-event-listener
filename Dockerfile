FROM adoptopenjdk/openjdk11:alpine-jre AS builder
ENV JAR_FILE=build/libs/*.jar
COPY $JAR_FILE application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk/openjdk11:alpine-jre
ARG PROFILE
RUN echo ${PROFILE}
ARG VERSION
RUN echo ${VERSION}
COPY --from=builder dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder application/ ./

ENV version=${VERSION}
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
CMD ["java", "org.springframework.boot.loader.JarLauncher"]
