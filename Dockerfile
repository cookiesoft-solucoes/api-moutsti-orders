# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR da aplicação para o contêiner
COPY target/api-moutsti-orders-1.0.0.jar app.jar

# Exponha a porta usada pela aplicação (ajuste para sua aplicação)
EXPOSE 8080

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
