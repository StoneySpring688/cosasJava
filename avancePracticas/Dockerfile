FROM maven:3.9-amazoncorretto-21

WORKDIR /app

COPY pom.xml .
COPY src ./src

CMD sh -c "mvn clean package -DskipTests && cp target/*.jar /export/"