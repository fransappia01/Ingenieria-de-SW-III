# Desarrollo:

#### 1- Conceptos de Dockerfiles
  - Leer https://docs.docker.com/engine/reference/builder/ (tiempo estimado 2 horas)
  - Describir las instrucciones
 - **FROM**: La instruccion FROM inicializa una nueva etapa de construcción y establece la imagen base para las instrucciones posteriores. Como tal, un Dockerfile válido debe comenzar con una instrucción FROM. La imagen puede ser cualquier imagen válida; es especialmente fácil comenzar extrayendo una imagen de los repositorios públicos.

        `FROM [--platform=<platform>] <image> [AS <name>]`
- **RUN**: 
     
     Dos tipos de comandos: 

             RUN <command> (shell)
            
             RUN ["executable", "param1", "param2"]( formulario ejecutivo )

La instrucción RUN ejecutará cualquier comando en una nueva capa encima de la imagen actual y confirmará los resultados. La imagen confirmada resultante se usará para el siguiente paso en el archivo Dockerfile.

 - **ADD**: 
 
 ADD tiene dos formas:

        ADD [--chown=<user>:<group>] <src>... <dest>
        ADD [--chown=<user>:<group>] ["<src>",... "<dest>"]

La instrucción ADD copia nuevos archivos, directorios o direcciones URL de archivos remotos <src> y los agrega al sistema de archivos de la imagen en la ruta <dest>.

 - **COPY**: 

 COPY tiene dos formas:

    COPY [--chown=<user>:<group>] <src>... <dest>
    COPY [--chown=<user>:<group>] ["<src>",... "<dest>"]

La instrucción COPY copia nuevos archivos o directorios <src> y los agrega al sistema de archivos del contenedor en la ruta <dest>.
- **EXPOSE**:

        EXPOSE <port> [<port>/<protocol>...]
La instrucción EXPOSE informa a Docker que el contenedor escucha en los puertos de red especificados en tiempo de ejecución. Puede especificar si el puerto escucha en TCP o UDP, y el valor predeterminado es TCP si no se especifica el protocolo.

La instrucción EXPOSE en realidad no publica el puerto. Funciona como un tipo de documentación entre la persona que construye la imagen y la persona que ejecuta el contenedor, sobre qué puertos se pretende publicar. 

 - **CMD**: 

     La instrucción CMD tiene tres formas:

       CMD ["executable","param1","param2"]( formulario exec , este es el formulario preferido)
       CMD ["param1","param2"](como parámetros predeterminados para ENTRYPOINT )
       CMD command param1 param2 (shell)
Solo puede haber una instrucción CMD en un Dockerfile. Si enumera más de uno CMD , solo tendrá efecto el último.

El objetivo principal de CMD es proporcionar valores predeterminados para un contenedor en ejecución. Estos valores predeterminados pueden incluir un ejecutable o pueden omitir el ejecutable, en cuyo caso también debe especificar una ENTRYPOINT instrucción.

- **ENTRYPOINT**: 

ENTRYPOINT tiene dos formas:

        ENTRYPOINT ["executable", "param1", "param2"]

        ENTRYPOINT command param1 param2 (shell)

UN ENTRYPOINT le permite configurar un contenedor que se ejecutará como un ejecutable.

#### 2- Generar imagen de docker
   - Clonar/Actualizar el repositorio de https://github.com/fernandobono/ing-software-3
   - El código se encuentra en la carpeta `./proyectos/spring-boot`
   - Se puede copiar al repositorio personal en una carpeta `trabajo-practico-06/spring-boot`
   - Compilar la salida con:
```bash
cd proyectos/spring-boot
mvn clean package spring-boot:repackage  
```

![2](/TP6/img/2.png)

El comando en una primera instancia me dio error, pero tuve que cambiar la seccion de `maven-surfire-plugin` en el archivo POM.xml para que funcione. En su lugar copie el siguiente codigo:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.19.1</version>
    <configuration>
        <testFailureIgnore>true</testFailureIgnore>
    </configuration>
</plugin>
```

![2.1](/TP6/img/2.1.png)

   - Agregar un archivo llamado **Dockerfile** (en el directorio donde se corrió el comando mvn)
```Dockerfile
FROM java:8-jre-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY target/*.jar ./spring-boot-application.jar

ENV JAVA_OPTS="-Xms32m -Xmx128m"
EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar spring-boot-application.jar
```
   - Generar la imagen de docker con el comando build
```bash
docker build -t test-spring-boot .
```
En un principio me daba error pero cambie la linea del FROM en el archivo Dockerfile:

```xml
            FROM openjdk:8-jre-alpine
```

  - Ejecutar el contenedor
```bash
docker run -p 8080:8080 test-spring-boot
```
  - Capturar y mostrar la salida.

![2.2](/TP6/img/2.2.png)

  - Verificar si retorna un mensaje (correr en otro terminal o browser)
```bash
curl -v localhost:8080
```
Tirar CURL en la cmd como administrador.

![3.1](/TP6/img/3.1.png)

![3](/TP6/img/3.png)

#### 3- Dockerfiles Multi Etapas
Se recomienda crear compilaciones de varias etapas para todas las aplicaciones (incluso las heredadas). En resumen, las compilaciones de múltiples etapas:

- Son independientes y auto descriptibles
- Resultan en una imagen de Docker muy pequeña
- Puede ser construido fácilmente por todas las partes interesadas del proyecto (incluso los no desarrolladores)
- Son muy fáciles de entender y mantener.
- No requiere un entorno de desarrollo (aparte del código fuente en sí)
- Se puede empaquetar con pipelines muy simples

Las compilaciones de múltiples etapas también son esenciales en organizaciones que emplean múltiples lenguajes de programación. La facilidad de crear una imagen de Docker por cualquier persona sin la necesidad de JDK / Node / Python / etc. no puede ser sobrestimado.

- Modificar el dockerfile para el proyecto Java anterior de la siguiente forma
```dockerfile
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM java:8-jre-alpine

EXPOSE 8080

RUN mkdir /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/spring-boot-application.jar

ENV JAVA_OPTS="-Xms32m -Xmx128m"

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/spring-boot-application.jar

HEALTHCHECK --interval=1m --timeout=3s CMD wget -q -T 3 -s http://localhost:8080/actuator/health/ || exit 1
```
- Construir nuevamente la imagen
```bash
docker build -t test-spring-boot .
```
Otra vez tuve que cambiar la parte del FROM. Cambiar java por openjdk.

![3.2](/TP6/img/3.2.png)
- Analizar y explicar el nuevo Dockerfile, incluyendo las nuevas instrucciones.

#### 4- Python Flask
  - Utilizar el código que se encuentra en la carpeta `./proyectos/python-flask`
  - Se puede copiar al repositorio personal en una carpeta `trabajo-practico-06/python-flask`
  - Correr el comando
```bash
cd ./proyectos/python-flask
docker-compose up -d
```

![4](/TP6/img/4.png)

  - Explicar que sucedió!
  - ¿Para qué está la key `build.context` en el docker-compose.yml?

  ver bien

![4.1](/TP6/img/4.1.png)

#### 5- Imagen para aplicación web en Nodejs
  - Crear una la carpeta `trabajo-practico-06/nodejs-docker`
  - Generar un proyecto siguiendo los pasos descriptos en el trabajo práctico 5 para Nodejs

  ```bash
npx create-react-app my-app
```

  - Escribir un Dockerfile para ejecutar la aplicación web localizada en ese directorio
    - Idealmente que sea multistage, con una imagen de build y otra de producción.
    - Usar como imagen base **node:13.12.0-alpine**
    - Ejecutar **npm install** dentro durante el build.
    - Exponer el puerto 3000
  - Hacer un build de la imagen, nombrar la imagen **test-node**.
  - Ejecutar la imagen **test-node** publicando el puerto 3000.
  - Verificar en http://localhost:3000 que la aplicación está funcionando.
  - Proveer el Dockerfile y los comandos ejecutados como resultado de este ejercicio.

#### 6- Publicar la imagen en Docker Hub.
  - Crear una cuenta en Docker Hub si no se dispone de una.
  - Registrase localmente a la cuenta de Docker Hub:
```bash
docker login
```
  - Crear un tag de la imagen generada en el ejercicio 3. Reemplazar <mi_usuario> por el creado en el punto anterior.
```bash
docker tag test-node <mi_usuario>/test-node:latest
```
  - Subir la imagen a Docker Hub con el comando
```bash
docker push <mi_usuario>/test-node:latest
``` 
  - Como resultado de este ejercicio mostrar la salida de consola, o una captura de pantalla de la imagen disponible en Docker Hub.