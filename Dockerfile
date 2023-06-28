FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/intelligent-commerce-1.0.jar
COPY ${JAR_FILE} IntelligentCommerce.jar
ENTRYPOINT ["java","-jar","IntelligentCommerce.jar"]