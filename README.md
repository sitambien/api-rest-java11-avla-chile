1.- Descripción

Servicio API REST, donde los endpoints corresponden a
un CRUD de usuarios, recibiendo y respondiendo en formato JSON.

2.- Tecnologías

Desarrollé en Java, Spring boot (2.7.10) y Gradle. La versión de java es la 11. Para documentar se utilizó swagger. Se puede consulta la documentación, una vez levantado el proyecto en: http://localhost:8080/swagger-ui/index.html. Hice los primeros request con Postman. La base de datos es en memoria y el motor es H2. También incluye el paquete de Docker.

3.- Instrucciones e instalación

Se debe tener instalado Java 11 y Gradle en el sistema

3.1. Para correr desde la consola de comandos

3.1.1. Comandos de para clonar el proyecto desde git

git clone https://github.com/sitambien/api-rest-java11-avla-chile.git

3.1.2. Se debe abrir la consola en el directorio raiz del proyecto

3.1.3. Se debe compilar la aplicacion con Gradle

./gradlew clean build

3.1.4. Y para ejecutarla:

./gradlew bootRun


3.2. Desde el IDE


3.2.1. Importar un proyecto desde el IDE.

3.2.2. Selecciona el directorio raíz de tu proyecto donde se encuentra el archivo build.gradle.

3.2.3. Compila y empaqueta tu aplicación en el IDE, utilizando las opciones de Gradle.

3.2.4. Ejecuta tu aplicación desde el IDE utilizando la opción de "Run" o "Debug".


3.3. Para levantar el proyecto con Docket


3.3.1. Comandos de para clonar el proyecto desde git

git clone https://github.com/sitambien/api-rest-java11-avla-chile.git

3.3.2. Se debe abrir la consola en el directorio raiz del proyecto

3.3.3. Se debe crear una imagen de Docker para la app.

docker build -t nombre-de-la-imagen:version .

3.3.4. Se pueden ver las imagenes creadas con el comando:

docker images

3.1.4. Y para ejecutarla:

docker run -p 8080:8080 codigo-de-la-imagen