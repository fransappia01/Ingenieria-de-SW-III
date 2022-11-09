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

En esta instancia vamos a realizar un pipeline que realice acciones en conjunto que ya vimos en trabajos practicos anteriores.

1. En primer lugar creamos una carpeta llamada `spring-boot-it` dentro deL directorio `Ingenieria-de-sw-III/TP12/`.

2. Dentro de esa carpeta ejecutamos el comando `npx create codeceptjs .`, y luego `npx codeceptjs init .`

```powershell
PS C:\Users\Francisco> cd '.\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\'
PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it> npx create-codeceptjs .
npx: installed 32 in 15.078s


 ╔═╗ ╦═╗ ╔═╗ ╔═╗ ╔╦╗ ╔═╗
 ║   ╠╦╝ ║╣  ╠═╣  ║  ║╣
 ╚═╝ ╩╚═ ╚═╝ ╩ ╩  ╩  ╚═╝

 ╔═╗ ╔═╗ ╔╦╗ ╔═╗ ╔═╗ ╔═╗ ╔═╗ ╔╦╗  ╦ ╔═╗
 ║   ║ ║  ║║ ║╣  ║   ║╣  ╠═╝  ║   ║ ╚═╗
 ╚═╝ ╚═╝ ═╩╝ ╚═╝ ╚═╝ ╚═╝ ╩    ╩  ╚╝ ╚═╝


 🔌 Supercharged End 2 End Testing 🌟

Creating CodeceptJS project in C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it

Powered by Playwright engine
package.json file does not exist in current dir, creating it...
Installing packages:  codeceptjs@3, @codeceptjs/ui, @codeceptjs/examples, @codeceptjs/configure, playwright@1

> playwright@1.27.1 install C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\node_modules\playwright
> node install.js


> core-js@2.6.12 postinstall C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\node_modules\core-js
> node -e "try{require('./postinstall')}catch(e){}"


> electron@15.5.7 postinstall C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\node_modules\electron
> node install.js

+ @codeceptjs/examples@1.2.1
+ @codeceptjs/configure@0.10.0
+ @codeceptjs/ui@0.4.7
+ codeceptjs@3.3.6
+ playwright@1.27.1
added 462 packages from 293 contributors and audited 464 packages in 122.662s

79 packages are looking for funding
  run `npm fund` for details

found 3 vulnerabilities (1 moderate, 2 high)
  run `npm audit fix` to fix them, or `npm audit` for details
Finished installing packages.

What's next?

Try CodeceptJS now with a demo project:
➕ npm run codeceptjs:demo - executes codeceptjs tests for a demo project
➕ npm run codeceptjs:demo:headless - executes codeceptjs tests headlessly (no window shown)
➕ npm run codeceptjs:demo:ui - starts codeceptjs UI application for a demo project

Initialize CodeceptJS for your project:
🔨 npx codeceptjs init - initialize codeceptjs for current project (required)
➕ npm run codeceptjs - runs codeceptjs tests for current project
➕ npm run codeceptjs:headless - executes codeceptjs tests headlessly (no window shown)
➕ npm run codeceptjs:ui - starts codeceptjs UI application for current project

PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it> npx codeceptjs init

  Welcome to CodeceptJS initialization tool
  It will prepare and configure a test environment for you

Installing to C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it
? Do you plan to write tests in TypeScript? Yes
? Where are your tests located? ./*_test.js
? What helpers do you want to use? Playwright
? Where should logs, screenshots, and reports to be stored? ./output
? Do you want localization for tests? (See https://codecept.io/translation/) English (no localization)
Configure helpers...
? [Playwright] Base url of site to be tested https://fransappia01-springboot.herokuapp.com/
? [Playwright] Show browser window Yes
? [Playwright] Browser in which testing will be performed. Possible options: chromium, firefox, webkit or electron chromium

Config created at C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\codecept.conf.ts
Directory for temporary output files created at './output'
Installing packages:  typescript, ts-node, @types/node
+ ts-node@10.9.1
+ @types/node@18.11.3
+ typescript@4.8.4
added 18 packages from 53 contributors, updated 1 package and audited 482 packages in 38.648s

79 packages are looking for funding
  run `npm fund` for details

found 3 vulnerabilities (1 moderate, 2 high)
  run `npm audit fix` to fix them, or `npm audit` for details
context
TypeScript Definitions provide autocompletion in Visual Studio Code and other IDEs
Definitions were generated in steps.d.ts

 Almost ready... Next step:
Creating a new test...
----------------------
? Feature which is being tested (ex: account, login, etc) spring-boot-heroku
? Filename of a test spring-boot-heroku_test.js

Test for spring-boot-heroku_test.js was created in C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP12\spring-boot-it\spring-boot-heroku_test.js

--
CodeceptJS Installed! Enjoy supercharged testing! 🤩
Find more information at https://codecept.io
```

La url definida es: https://fransappia01-springboot.herokuapp.com/

3. Luego modificamos el archivo `spring-boot-heroku_test.js` como se indica en el TP10:


```javascript
Feature('spring-boot');

const expect = require('chai').expect;
const {I} = inject();

Scenario('Verify a successful call', async () => {
	const res = await I.sendGetRequest('/');
	expect(res.status).to.eql(200);
});

Scenario('Verify return value', async () => {
	const res = await I.sendGetRequest('/');
	//console.log(res);
	expect(res.data.message).to.eql('Spring boot says hello from a Docker container');
});
```

Y tambien reemplazar la seccion helpers de codecept.conf.js como se indica pero con la nueva url.

```javascript
	helpers: {
		REST: {
			endpoint: "https://fransappia01-springboot.herokuapp.com/",
			onRequest: () => {
			}
		}
	}
  ```

  4. Agregar esta parte de codigo en el archivo `codecept.conf.ts`: 

```javascript
  	mocha:  {
  "reporterOptions": {
    "codeceptjs-cli-reporter": {
      "stdout": "-",
      "options": {
        "steps": true,
      }
    },
    "mocha-junit-reporter": {
      "stdout": "./output/console.log",
      "options": {
        "mochaFile": "./output/result.xml"
      },
      "attachments": true //add screenshot for a failed test
  	  }
  	}
  }
```
Pipeline:

```
pipeline {
  agent any

  tools {
      // Install the Maven version configured as "M3" and add it to the path.
       maven "M3"
  }
  stages {

      stage('Build') {
           steps {
              // Get spring-boot folder from github repo
              git 'https://github.com/fransappia01/Ingenieria-de-SW-III'
                  
               dir('spring-boot') {
                   sh("mvn package")
              }
          }
          post {
               // If Maven was able to run the tests, even if some of the test
               // failed, record the test results and archive the jar file.
              success {
                  dir('tp12/spring-boot') {
                       archiveArtifacts 'target/*.jar'
                  }
              }
          }
      stage('Heroku Push and Deploy') {
          steps {
              dir('tp12/spring-boot') {
              // sh "heroku create heroku-app"
              sh "heroku container:push web --app=heroku-app"
              sh "heroku container:release web --app=heroku-app"
              }
          }
      }
      stage('Install Dependencies') {
          steps {
              dir('TP12/spring-boot-it') {
                  sh "npm install"
                  sh "npm i mocha-junit-reporter mocha-multi --save"
              }
          }
      }
      stage('Run Integration Tests') {
          steps {
              dir('TP12/spring-boot-it') {
                  sh "npx codeceptjs run --steps --reporter mocha-multi"
              }
          }
      }
      stage('Collect Integration Tests Report') {
          steps {
              dir('TP12/spring-boot-it/output') {
                  junit 'result.xml'
              }
          }
      }
  }
  ```