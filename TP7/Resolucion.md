# Desarrollo

## 1- Poniendo en funcionamiento Jenkins
  - Bajar la aplicación y ejecutarla (ejemplo para Linux):
```bash
export JENKINS_HOME=~/jenkins
mkdir -p $JENKINS_HOME
cd $JENKINS_HOME
wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war
java -jar jenkins.war --httpPort=8081
```
  - Se puede también ejecutar en contenedor de Jenkins (pero para construir imágenes de Docker, el proceso se complica un poco):

```bash
# Windows
mkdir -p C:\jenkins
docker run -d -p 8081:8080 -p 50000:50000 -v C:\jenkins:/var/jenkins_home jenkins/jenkins:lts
```

```bash
# Linux / Mac OS
mkdir -p ~/jenkins
docker run -d -p 8081:8080 -p 50000:50000 -v ~/jenkins:/var/jenkins_home jenkins/jenkins:lts
```
  - Una vez en ejecución, abrir http://localhost:8081

![1](/TP7/img/1.png)

  - Inicialmente deberá especificar el texto dentro del archivo ~/jenkins/secrets/initialAdminPassword
```bash
cat ~/jenkins/secrets/initialAdminPassword
```

![1.1](/TP7/img/1.1.png)

  - Instalar los plugins por defecto
![alt text][imagen]

[imagen]:  jenkins-plugins.png  
  - Crear el usuario admin inicial. Colocar cualquier valor que considere adecuado.
![alt text][imagen1]


![1.2](/TP7/img/1.2.png)

Interfaz:
![1.3](/TP7/img/1.3.png)


[imagen1]:  jenkins-admin.png    

 - Se aconseja perisistir la variable **JENKINS_HOME**, ya sea por ejemplo en .bashrc o en las variables de entorno de Windows.
## 2- Conceptos generales
  - Junto al Jefe de trabajos prácticos:
  - Explicamos los diferentes componentes que vemos en la página principal
  - Analizamos las opciones de administración de Jenkins

## 3- Instalando Plugins y configurando herramientas
  - En Administrar Jenkins vamos a la sección de Administrar Plugins
  - De la lista de plugins disponibles instalamos **Docker Pipeline**

  ![3](/TP7/img/3.png)

  - Instalamos sin reiniciar el servidor.

  ![3.1](/TP7/img/3.1.png)

  - Abrir nuevamente página de Plugins y explorar la lista, para familiarizarse qué tipo de plugins hay disponibles.
  - En la sección de administración abrir la opción de configuración de herramientas
  - Agregar maven con el nombre de **M3** y que se instale automáticamente.

  ![3.2](/TP7/img/3.2.png)

## 4- Creando el primer Pipeline Job
  - Crear un nuevo item, del tipo Pipeline con nombre **hello-world**

  ![4](/TP7/img/4.png)

  - Una vez creado el job, en la sección Pipeline seleccionamos **try sample Pipeline** y luego **Hello World**

  ![4.1](/TP7/img/4.1.png)
  - Guardamos y ejecutamos el Job
  - Analizar la salida del mismo

  ![4.2](/TP7/img/4.2.png)

  ![4.3](/TP7/img/4.3.png)

## 5- Creando un Pipeline Job con Git y Maven
  - Similar al paso anterior creamos un ítem con el nombre **simple-maven**

  ![5](/TP7/img/5.png)

  - Elegir **Git + Maven** en la sección **try sample Pipeline**

  ![5.1](/TP7/img/5.1.png)

  - Guardar y ejecutar el Job
  - Analizar el script, para identificar los diferentes pasos definidos y correlacionarlos con lo que se ejecuta en el Job y se visualiza en la página del Job.

Tardo 4 minutos en realidad, pero hubo un bug y el reloj seguia contando:

  ![5.3](/TP7/img/5.3.png)

  ![5.4](/tp7/img/5.4.png)

Script:

```javascript
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                //lo que se hace aca es descargar el repositorio de git
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'

                // Run Maven on a Unix agent.
                //limpia los paquetes si tenemos sistema basado en unix, y obtenemos un .jar
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                //limpia los paquetes si tenemos sistema windows, y obtenemos un .jar

                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
                //Al final de la ejecucion junta todos los resultados de los test cases que se generan como parte de la ejecucion de ese clean package, usando el plugin Junit. El archiveArtifacts guarda todo lo que se genera en la carpeta target.
            }
        }
    }
}
```

## 6- Utilizando nuestros proyectos
  - Utilizando lo aprendido en el ejercicio 5
  - Crear un Job que construya el proyecto **spring-boot** del [trabajo práctico 6](06-construccion-imagenes-docker.md).
  - Obtener el código desde el repositorio de cada alumno (se puede crear un repositorio nuevo en github que contenga solamente el proyecto maven).
  - Generar y publicar los artefactos que se producen.

En un principio, se me generaba el archivo .tar de manera INESTABLE. Por lo tanto, para solucionar esto tuve que cambiar el archivo POM.XML de la carpeta spring-boot en mi repo. Tuve que agregar las siguientes dependencias:

![6.1](/TP7/img/6.1.png)

Como resultado, anduvo correctamente:

  ![6](/TP7/img/6.png)
  - Como resultado de este ejercicio proveer el script en un archivo **spring-boot/Jenkinsfile**
 
                                    Hecho


## 7- Utilizando nuestros proyectos con Docker
  - Extender el ejercicio 6
  - Generar y publicar en Dockerhub la imagen de docker ademas del Jar.
  - Se puede utilizar el [plugin de docker](https://docs.cloudbees.com/docs/admin-resources/latest/plugins/docker-workflow) o comandos de shell.
  - No poner usuario y password en el pipeline en texto plano, por ejemplo para conectarse a DockerHub, utilizar [credenciales de jenkins](https://github.com/jenkinsci/credentials-plugin/blob/master/docs/user.adoc) en su lugar.

Creamos las credenciales a traves de Jetkins

![7](/TP7/img/7.png)

  - Como resultado de este ejercicio proveer el script en un archivo **spring-boot/Jenkinsfile**
  - Referencia: https://tutorials.releaseworksacademy.com/learn/building-your-first-docker-image-with-jenkins-2-guide-for-developers

  Luego de muchisimos errores, pude realizar este ejercicio correctamente. Paso a explicar como los solucione:

  1) El primer error que obtuve fue de ``docker not found``. Para solucionar esto lo primero que hice fue crear mi prpia imagen de jenkins e instalar docker adentro de ella, el dockerfile que utilize fue el siguiente (se encuentra en mi repo tambien ../tp7/docker):

```Dockerfile
FROM jenkins/jenkins:lts
USER root
RUN apt update && apt install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
RUN mkdir -p /etc/apt/keyrings
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" \
  | tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt update && apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
RUN usermod -aG docker jenkins
```

Luego, realice un build con el comando `docker image build -t ttest-spring-boot`.

Despues, para levantar el jenkins corri el siguiente comando desde la carpeta de jenkins:  `docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock -v C:/jenkins:/var/jenkins_home -p 8081:8080 -p 50000:50000 testt-spring-boot`

![7.2](/TP7/img/7.2.png)


2) En segundo lugar, tuve el error: `Error response from daemon: Get https://registry-1.docker.io/v2/library/hello-world 
/manifests/latest: unauthorized: incorrect username or password`

Este error, creo que se debe a que a la hora de crear las credenciales puse la contraseña correspondiente a mi cuenta de jenkins, cuando debo poner mi usuario y contraseña de mi cuenta de DOCKER. Sin embargo, lo que hice fue borrar la credencial anterior y crear otra nueva. En ese caso, funcionó.

![7.1](/TP7/img/7.1.png)
![7](/TP7/img/7%20anduvo.png)