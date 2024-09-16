# Use uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do container
WORKDIR /app

# Copie o arquivo pom.xml e as dependências do projeto
COPY pom.xml /app
COPY mvnw /app
COPY .mvn /app/.mvn

# Baixe as dependências do Maven
RUN ./mvnw dependency:resolve

# Copie o código-fonte do projeto
COPY src /app/src

# Compile e crie o pacote da aplicação
RUN ./mvnw package -DskipTests

# Exponha a porta que o Spring Boot irá usar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/login-auth-api-0.0.1-SNAPSHOT.jar"]
