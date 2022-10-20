# Desarrollo:

#### 1- Configurando Heroku
  - Crear una cuenta en Heroku https://dashboard.heroku.com
  - Instalar la utilidad de línea de comando de Heroku: https://devcenter.heroku.com/articles/heroku-cli
  - Abrir una línea de comandos y registrase con la aplicación CLI
```
heroku login
heroku container:login
```
![1](/TP12/img/1.png)

#### 2- Creando y Desplegando la aplicación Payroll
  - Modificar el archivo Dockerfile de nuestra aplicación para que sea compatible con Heroku (necesitamos definir una variable de entorno para el puerto donde correrá el servicio):
```
FROM java:8-jre-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY target/*.jar ./spring-boot-application.jar

ENV PORT=8080

EXPOSE 8080

CMD ["java", "-Xms32m", "-Xmx128m", "-jar", "-Dserver.port=${PORT}", "-Djava.security.egd=file:/dev/./urandom", "spring-boot-application.jar"]
```
Se utlizo este Dockerfile:

```Dockerfile
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM openjdk:8-jre-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY target/*.jar ./spring-boot-application.jar

ENV PORT=8080

EXPOSE 8080

CMD ["java", "-Xms32m", "-Xmx128m", "-jar", "-Dserver.port=${PORT}", "-Djava.security.egd=file:/dev/./urandom", "spring-boot-application.jar"]
```

  - Abrir una línea de comandos y cambiar el directorio a donde se encuentra nuestra aplicación
```
cd ./proyectos/spring-boot
```
  - Crear una nueva aplicación en Heroku
```
heroku create
```
  - Esto creara un aplicación con un nombre determinando, por ejemplo **ancient-reaches-06178**
  - Generar y subir la imagen de Docker al registry de Heroku, desde este registry se desplegará la aplicación en Heroku
```
heroku container:push web --app=ancient-reaches-06178
```

```
PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\spring-boot> heroku container:push web --app=still-waters-32768
=== Building web (C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\spring-boot\Dockerfile)
[+] Building 0.7s (2/2)
 => [internal] load build definition from Dockerfile                                                                                                             0.3s
 => => transferring dockerfile: 603B                                                                                                                             0.0s[+] Building 0.8s (2/3)
 => [internal] load build definition from Dockerfile                                                                                                            0.3ss => => transferring dockerfile: 603B                                                                                                                            0.0[+] Building 1.0s (2/3)
 => [internal] load build definition from Dockerfile                                                                                                           0.3s[+] Building 1.1s (2/3)
 => [internal] load build definition from Dockerfile                                                                                                          0.3s[+] Building 4.0s (4/4) FINISHED
 => [internal] load build definition from Dockerfile                                                                                                         0.3s
 => => transferring dockerfile: 603B                                                                                                                         0.0ss
 => [internal] load .dockerignore                                                                                                                            0.2s0
 => => transferring context: 2B                                                                                                                              0.0s.
 => ERROR [internal] load metadata for docker.io/library/java:8-jre-alpine                                                                                   3.2s
 => [auth] library/java:pull token for registry-1.docker.io                                                                                                  0.0s
------
 > [internal] load metadata for docker.io/library/java:8-jre-alpine:
------
failed to solve with frontend dockerfile.v0: failed to create LLB definition: docker.io/library/java:8-jre-alpine: not found
 !    Error: docker build exited with Error: 1
PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\spring-boot> heroku container:push web --app=still-waters-32768
=== Building web (C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\spring-boot\Dockerfile)
[+] Building 5.9s (10/10) FINISHED
 => [internal] load build definition from Dockerfile                                                                                                         0.1s
 => => transferring dockerfile: 606B                                                                                                                         0.0s
 => [internal] load .dockerignore                                                                                                                            0.1s
 => => transferring context: 2B                                                                                                                              0.0s
 => [internal] load metadata for docker.io/library/openjdk:8-jre-alpine                                                                                      4.2s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                               0.0s
 => [internal] load build context                                                                                                                            1.2s
 => => transferring context: 21.72MB                                                                                                                         1.2s
 => [stage-1 1/4] FROM docker.io/library/openjdk:8-jre-alpine@sha256:f362b165b870ef129cbe730f29065ff37399c0aa8bcab3e44b51c302938c9193                        0.0s
 => CACHED [stage-1 2/4] RUN apk add --no-cache bash                                                                                                         0.0s
 => CACHED [stage-1 3/4] WORKDIR /app                                                                                                                        0.0s
 => CACHED [stage-1 4/4] COPY target/*.jar ./spring-boot-application.jar                                                                                     0.0s
 => exporting to image                                                                                                                                       0.0s
 => => exporting layers                                                                                                                                      0.0s
 => => writing image sha256:2d4bfc72469d96f22dc7dcb46af4a20c42455c140f90f04c2d2f86434b4fe8d0                                                                 0.0s
 => => naming to registry.heroku.com/still-waters-32768/web                                                                                                  0.0s
=== Pushing web (C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\spring-boot\Dockerfile)
Using default tag: latest
The push refers to repository [registry.heroku.com/still-waters-32768/web]
8ed419710d55: Pushed
ab34f8942626: Pushed
7ca8b1bde424: Pushed
edd61588d126: Pushed
9b9b7f3d56a0: Pushed
f1b5933fe4b5: Pushed
latest: digest: sha256:f6711521f2224a49817eaec4d4fa2f9cdd85786c91af73bf8ce284e3780c8b7b size: 1577
Your image has been successfully pushed. You can now release it with the 'container:release' command.
```
Entrando a https://still-waters-32768.herokuapp.com/

![2.2](/TP12/img/2.2.png)

  - Una vez terminada la operación, procedemos a desplegar la aplicación
```
heroku container:release web --app=ancient-reaches-06178
```
  - Nuestra aplicación estará ahora disponible en https://ancient-reaches-06178.herokuapp.com/

Luego de hacer release, 

![2.3](/TP12/img/2.3.png)


```
$ curl https://still-waters-32768.herokuapp.com/employees

{"message":"Spring boot says hello from a Docker container"}
$
```
  - Con esto vemos que está retornando el mensaje esperado.

![2.4](/TP12/img/2.4.png)

#### 3- Integrar el despliegue en Jenkins
  - Agregar un Job o un Stage para desplegar la aplicación en Heroku
  - Ejecutar los tests de Integración / UAT desde Jenkins y colectar los resultados utilizando esta instancia de la aplicación.

