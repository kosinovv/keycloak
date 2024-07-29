FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnv clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/kosinov-keycloak*.jar /usr/local/lib/kosinov-keycloak.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/kosinov-keycloak.jar"]