# Desarrollo

#### 1- Instalar Java JDK si no dispone del mismo. 
  - Java 8 es suficiente, pero puede utilizar cualquier versión.
  - Utilizar el instalador que corresponda a su sistema operativo 
  - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
  - Agregar la variable de entorno JAVA_HOME
    - En Windows temporalmente se puede configurar
    ```bash
      SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_221
    ```
    - O permanentemente entrando a **Variables de Entorno** (Winkey + Pausa -> Opciones Avanzadas de Sistema -> Variables de Entorno)
  - Otros sistemas operativos:
    - https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html
    - https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04


#### 2- Instalar Maven
- Instalar maven desde https://maven.apache.org/download.cgi (última versión disponible 3.6.1)
- Descomprimir en una carpeta, por ejemplo C:\tools
- Agregar el siguiente directorio a la variable de entorno PATH, asumiendo que los binarios de ant están en C:\tools\apache-maven-3.6.1\bin

  ```bash   
    SET PATH=%PATH%;C:\tools\apache-maven-3.6.1\bin
  ```  
- Se puede modificar permanentemente la variable PATH entrando a (Winkey + Pausa -> Opciones Avanzadas de Sistema -> Variables de Entorno)
- En Linux/Mac se puede agregar la siguiente entrada al archivo ~/.bash_profile

  ```bash
  export PATH=/opt/apache-maven-3.6.1/bin:$PATH
  ```

![1](/TP5/img/1.png)


#### 3- Introducción a Maven
- Qué es Maven?

Maven es una herramienta de software para la gestión y construcción de proyectos Java. Maven utiliza un Project Object Model (POM) para describir el proyecto de software a construir, sus dependencias de otros módulos y componentes externos, y el orden de construcción de los elementos. Viene con objetivos predefinidos para realizar ciertas tareas claramente definidas, como la compilación del código y su empaquetado.

- Qué es el archivo POM?

POM (Project Object Model) es la representación XML de un proyecto Maven. Contiene toda la información necesaria sobre un proyecto, así como las configuraciones de complementos que se utilizarán en el momento de compilación.

    1. modelVersion: Contiene la versión del modelo del POM.
    2. groupId: Identificacion de la organizacion que desarrolla el proyecto. Esta informacion va a generar la estructura del paquete.
    3. artifactId: Define un módulo maven
    4. versionId: version del artifact

- Repositorios Local, Central y Remotos http://maven.apache.org/guides/introduction/introduction-to-repositories.html

Hay exactamente dos tipos de repositorios: locales y remotos :

1. El repositorio local es un directorio en la computadora donde se ejecuta Maven. Almacena en caché las descargas remotas y contiene artefactos de compilación temporales que aún no ha publicado.

2. Los repositorios remotos se refieren a cualquier otro tipo de repositorio, al que se accede mediante una variedad de protocolos como file://y https://. Estos repositorios pueden ser un repositorio verdaderamente remoto configurado por un tercero para proporcionar sus artefactos para descargar (por ejemplo, repo.maven.apache.org ). 

    Otros repositorios "remotos" pueden ser repositorios internos configurados en un servidor de archivos o HTTP dentro de su empresa, utilizados para compartir artefactos privados entre equipos de desarrollo y para lanzamientos.

    Los repositorios locales y remotos están estructurados de la misma manera para que los scripts se puedan ejecutar en cualquier lado o se puedan sincronizar para su uso sin conexión. Sin embargo, el diseño de los repositorios es completamente transparente para el usuario de Maven.

3. Repositorio central: localizado en http://repo.maven.apache.org/maven2/. Cuando se compila, maven primero intenta encontrar la dependencia en el repositorio local. Si no esta ahí, por defecto, activa la descarga desde este repositorio central. Es el repositorio remoto por defecto.

- Entender Ciclos de vida de build

Maven se basa en el concepto central de un ciclo de vida de construcción. Lo que esto significa es que el proceso para construir y distribuir un artefacto en particular (proyecto) está claramente definido.

Para la persona que construye un proyecto, esto significa que solo es necesario aprender un pequeño conjunto de comandos para construir cualquier proyecto de Maven, y el POM se asegurará de que obtenga los resultados que desea.

Hay tres ciclos de vida de compilación integrados: **default**, **clean** y **site**.

     - default: maneja el deployment del proyecto (la implementacion).
     - clean: maneja la limpieza del proyecto.
     - site: maneha la creacion del sitio web de su proyecto.

  - Referencia: http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Build_Lifecycle_Basics
- Comprender las fases de un ciclo de vida, por ejemplo, default:

| Fase de build | Descripción                                                                                                                            |
|---------------|----------------------------------------------------------------------------------------------------------------------------------------|
| validate      | valida si el proyecto está correcto y toda la información está disponible                                                             |
| compile       | compila el código fuente del proyecto                                                                                 |
| test          | prueba el código fuente compilado utilizando un marco de prueba de unidad adecuado. Estas pruebas no deberían requerir que el código se empaquete o implemente |
| package       | toma el código compilado y lo empaqueta en su formato distribuible, como un JAR.                                                     |
| verify        | ejecuta cualquier verificación de los resultados de las pruebas de integración para garantizar que se cumplan los criterios de calidad                                                      |
| install       | instal1 el paquete en el repositorio local, para usarlo como dependencia en otros proyectos localmente                                       |
| deploy        | hecho en el entorno de compilación, copia el paquete final en el repositorio remoto para compartirlo con otros desarrolladores y proyectos.      |

- Copiar el siguiente contenido a un archivo, por ejemplo ./trabajo-practico-02/maven/vacio/pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ar.edu.ucc</groupId>
    <artifactId>proyecto-01</artifactId>
    <version>0.1-SNAPSHOT</version>
</project>
```

- Ejecutar el siguiente comando en el directorio donde se encuentra el archivo pom.xml
```
mvn clean install
```
![3](/TP5/img/3.png)
- Sacar conclusiones del resultado

`mvn clean install` para decirle a Maven que haga el clean en cada módulo antes de ejecutar el install acción para cada módulo. Lo que hace esto es limpiar cualquier archivo compilado que tengas, asegurándote de que realmente estás compilando cada módulo desde cero. Es decir, el clean limpia todo y luego con install instalamos la serie de archivos desde el repositorio central que se indican dentro del pom.xml. Se crea el archivo .jar.

#### 4- Maven Continuación

- Generar un proyecto con una estructura inicial:

```bash
mvn archetype:generate -DgroupId=ar.edu.ucc -DartifactId=ejemplo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
**PARA ESTE COMANDO EN POWERSHELL DE WINDOWS UTILIZAR COMILLAS**
![4](/TP5/img/4.png)

![4.1](/TP5/img/4.1.png)
- Analizar la estructura de directorios generada:

Con el comando `tree` podemos ver la siguiente estructura:
```
.
└── ejemplo
    ├── pom.xml
    └── src
        ├── main
        │   └── java
        │       └── ar
        │           └── edu
        │               └── ucc
        │                   └── App.java
        └── test
            └── java
                └── ar
                    └── edu
                        └── ucc
                            └── AppTest.java

12 directories, 3 files
```
Una de las principales características de Maven es que sigue el patrón de convención sobre configuración. Esto significa que debemos situar nuestras clases, tests o recursos en un lugar en concreto, para que luego Maven sea capaz de tratarlos correctamente.


Maven tiene cuatro carpetas fuente por defecto:

1.  `src/main/java` : donde guardaremos nuestras clases java fuente. Debajo de esta carpeta situaremos nuestras clases en distintos paquetes.


2. `src/main/resources` : aquí almacenaremos los recursos (ficheros xml, ficheros de propiedades, imagenes, …) que pueda necesitar las clases java de nuestro proyecto. Igualmente aquí tienen que ir los ficheros de configuración de Spring o Hibernate por ejemplo.

3.  `src/test/java` : en dicha carpeta se guardan las clases de test que se encargarán de probar el correcto funcionamiento de nuestra aplicación. Aquí por ejemplo podemos guardar nuestros test unitarios de JUnit.

4.  `src/test/resources` : en esta carpeta guardamos los recursos que usan los recursos.

- Compilar el proyecto

```bash
mvn clean package
```
A la hora de tirar este comando me salia un error, pero lo solucioné agregando estas lineas de codigo al archivo pom.xml:
```xml
<properties>
     <maven.compiler.source>1.8</maven.compiler.source>
     <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

![5](/TP5/img/5.png)


Fuente de la solucion al problema: https://stackoverflow.com/questions/53034953/error-source-option-5-is-no-longer-supported-use-6-or-later-on-maven-compile
- Analizar la salida del comando anterior y luego ejecutar el programa

Salida: 

![5.1](/TP5/img/5.1.png)

```
java -cp target/ejemplo-1.0-SNAPSHOT.jar ar.edu.ucc.App
```

![5.2](/TP5/img/5.2.png)

#### 6- Manejo de dependencias

- Crear un nuevo proyecto con artifactId **ejemplo-uber-jar**

![6](/TP5/img/6.png)

- Modificar el código de App.java para agregar utilizar una librería de logging:

```java
package ar.edu.ucc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Logger log = LoggerFactory.getLogger(App.class);
        log.info("Hola Mundo!");
    }
}
```

- Compilar el código e identificar el problema.

Faltan las dependencias en el archivo pom.xml. Nunca se definieron esas dependencias en el pom.xml, que es necesario para que puedan ser usadas en la app.java

![6.1](/TP5/img/6.1.png)

- Agregar la dependencia necesaria al pom.xml

```xml
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.1</version>
    </dependency>
```

Ahora funciona correctamente

![6.2](/TP5/img/6.2.png)

- Verificar si se genera el archivo jar y ejecutarlo

```bash
java -cp target\ejemplo-uber-jar-1.0-SNAPSHOT.jar ar.edu.ucc.App
```
- Sacar conclusiones y analizar posibles soluciones

![6.6](/TP5/img/66.png)
Nos da error porque no deifnimos las librerias de LoggerFactory.


- Ahora, cjecutar la clase con el siguiente comando (en windows reemplazar `$HOME` por `%USERPROFILE%`, y separar por `;` en lugar de `:`)
```bash
 java -cp target/ejemplo-uber-jar-1.0-SNAPSHOT.jar:$HOME/.m2/repository/org/slf4j/slf4j-api/1.7.22/slf4j-api-1.7.22.jar:$HOME/.m2/repository/ch/qos/logback/logback-classic/1.2.1/logback-classic-1.2.1.jar:$HOME/.m2/repository/ch/qos/logback/logback-core/1.2.1/logback-core-1.2.1.jar ar.edu.ucc.App
```

No  me dejo tirar este comando, por lo tanto tuve que agregar unas lineas de codigo en el archivo pom.xml.
![6.9](/TP5/img/6.9.png)

Fuente: https://maven.apache.org/plugins/maven-shade-plugin/examples/includes-excludes.html
- Verificar que ahora resueltos los classpath la aplicación muestra el mensaje correcto

![6.10](/TP5/img/6.10.png)

- Implementar la opción de uber-jar: https://maven.apache.org/plugins/maven-shade-plugin/


```xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>2.0</version>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
            <configuration>
              <finalName>${project.artifactId}</finalName>
              <transformers>
                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                  <mainClass>ar.edu.ucc.App</mainClass>
                </transformer>
              </transformers>
              <minimizeJar>false</minimizeJar>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```
- Volver a generar la salida y probar ejecutando

```bash
java -jar target\ejemplo-uber-jar.jar
```
Este codigo poniendolo en el pom.xml me funciono correctamente utilizandolo desde la interfaz de Eclipse, pero por la terminal me tiraba el error de la foto [6.6]
Por lo tanto, mi pom.xml sigue con el codigo que anduvo bien tanto en Eclipse como en la terminal.

#### 7- Utilizar una IDE
  - Importar el proyecto anterior en Eclipse o Intellij como maven project:
    - Si no dispone de Eclipse puede obtenerlo desde este link http://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-ee-developers
    - Para importar, ir al menú Archivo -> Importar -> Maven -> Proyecto Maven Existente:
![alt text](./import-existing-maven.png)
    - Seleccionar el directorio donde se encuentra el pom.xml que se generó en el punto anterior. Luego continuar:
![alt text](./path-to-pom.png)

  - Familiarizarse con la interfaz grafica
    - Ejecutar la aplicación
    - Depurar la aplicación
    - Correr unit tests y coverage
    - Ejecutar los goals de maven
    - Encontrar donde se puede cambiar la configuración de Maven.
    - etc.

![6.7](/TP5/img/6.7.png)

![6.8](/TP5/img/6.8.png)

#### 8- Ejemplo con nodejs

- Instalar Nodejs: https://nodejs.org/en/

- Crear una nueva aplicación
```bash
npx create-react-app my-app
```

- Ejecutar la aplicación
```bash
cd my-app
npm start
```

- La aplicación web estará disponible en http://localhost:3000

- Analizar el manejo de paquetes y dependencias realizado por npm.


#### 9- Ejemplo con python
- Instalar dependencias (Ejemplo Ubuntu) varía según el OS:
```
sudo apt install build-essential python3-dev
pip3 install cookiecutter
```
- Correr el scaffold
```bash
$ cookiecutter https://github.com/candidtim/cookiecutter-flask-minimal.git
application_name [Your Application]: test
package_name [yourapplication]: test
$
```
- Ejecutar la aplicación
```bash
cd test
make run
```
- Acceder a la aplicación en: http://localhost:5000/
- Explicar que hace una tool como cookiecutter, make y pip.

#### 10- Build tools para otros lenguajes
- Hacer una lista de herramientas de build (una o varias) para distintos lenguajes, por ejemplo (Rust -> cargo)
- Elegir al menos 10 lenguajes de la lista de top 20 o top 50 de tiobe: https://www.tiobe.com/tiobe-index/


#### 11- Presentación

- Subir todo el código, ejemplos y respuestas a una carpeta trabajo-practico-05.

> Tip: Agregar un archivo .gitignore al repositorio para evitar que se agreguen archivos que son resultado de la compilación u otros binarios, que no son necesarios, al mismo.