# Proyecto PGVNoel
Este es un proyecto desarrollado con Spring Boot y Gradle.

## Requisitos
Java 11 o superior.
Gradle 7 o superior (opcional si no tienes Gradle, puedes usar el wrapper de Gradle).
Base de datos configurada (por ejemplo, MySQL, PostgreSQL, etc.).

## Configuración del Proyecto
Clonación del Repositorio
Primero, clona este repositorio en tu máquina local:

git clone https://github.com/Noelregueiragerpe/PGVNoel.git
cd PGVNoel
## Configuración de la Base de Datos
Para configurar la conexión a la base de datos, abre el archivo src/main/resources/application.properties y agrega los detalles de tu base de datos:

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/tu_basededatos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración de JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

# Otros parámetros de configuración si es necesario
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
Asegúrate de reemplazar los valores tu_basededatos, tu_usuario, y tu_contraseña con los de tu base de datos.

Construir el Proyecto
Para compilar el proyecto y generar un archivo JAR, usa Gradle:

./gradlew build
Este comando descargará todas las dependencias necesarias y creará el archivo JAR en la carpeta build/libs.

Ejecutar el Proyecto
Una vez que hayas compilado el proyecto, puedes ejecutarlo con:

./gradlew bootRun
Este comando iniciará el servidor de Spring Boot y estará disponible en http://localhost:8080.

Si prefieres ejecutar el archivo JAR directamente después de compilar, puedes usar:

java -jar build/libs/tu-archivo.jar
Reemplaza tu-archivo.jar con el nombre del archivo JAR generado en la carpeta build/libs.

Otros Comandos Útiles
Limpiar el proyecto (elimina archivos compilados anteriores):
./gradlew clean
