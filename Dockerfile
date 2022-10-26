FROM openjdk:11
WORKDIR /app
COPY pom.xml ./
COPY . .
CMD ["java", "/src/main/java/com/smart/SmartcontactmanagerApplication.java" ]