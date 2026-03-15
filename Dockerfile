ARG JDK_IMAGE=eclipse-temurin:25-jdk-jammy

FROM ${JDK_IMAGE} AS builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

RUN java -Djarmode=layertools -jar app.jar extract


FROM ${JDK_IMAGE}

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
