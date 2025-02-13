# Usar uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Configurar diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pelo Maven/Gradle para dentro do container
COPY target/*.jar app.jar

# Expor a porta que sua aplicação usa (ajuste se necessário)
EXPOSE 8080

# Definir o comando correto para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
