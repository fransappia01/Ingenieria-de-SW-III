## 4- Desarrollo:

#### 1- Pros y Contras
  - Listar los pros y contras de este tipo de herramientas
  - Sacar conclusiones


Ventajas:

    - Alta disponibilidad: nos permite un acceso constante a la aplicacion.
    - Bajo costo: el alojamiento en la nube es más económico que el alojamiento local
    - Escalabilidad

Desventajas:

    - Disponibilidad de datos no es para todos: se limita la posibilidad de ajustar el servidor a las necesidades individuales.
    - Los costos pueden aumentar con el tiempo luego de haber comenzado el desarrollo del producto. Al depender del trafico de red --> a mayor trafico, mayores costos.

#### 2- Configurando GitHub Actions
  - Repetir el ejercicio 6 del trabajo práctico [trabajo práctico 7](07-servidor-build.md) para el proyecto **spring-boot**, pero utilizando GitHub Actions.
  - En GitHub, en el repositorio donde se encuentra la aplicación **spring-boot**, ir a la opción **Actions** y crear un nuevo `workflow`.
  - El nombre de archivo puede ser build.xml y tendrá un contenido similar al siguiente (el path donde se encuentra el código puede ser diferente):

```yaml
# This is a basic workflow to help you get started with Actions

name: CI

# Controls when the workflow will run
on:
  # Triggers the workflow on push or pull request events but only for the master branch
  push:
    paths:
    - 'proyectos/spring-boot/**'
    branches: [ master ]
  pull_request:
    paths:
    - 'proyectos/spring-boot/**'  
    branches: [ master ]

  # Allows you to run this workflow manually from the Actions tab
  workflow_dispatch:

# A workflow run is made up of one or more jobs that can run sequentially or in parallel
jobs:
  # This workflow contains a single job called "build"
  build:
    # The type of runner that the job will run on
    runs-on: ubuntu-latest

    # Steps represent a sequence of tasks that will be executed as part of the job
    steps:
      # Checks-out your repository under $GITHUB_WORKSPACE, so your job can access it
      - uses: actions/checkout@v2

      # Install Java JDK with maven
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
          cache: maven
          
      # Compile the application
      - name: Build with Maven
        run: |
          cd proyectos/spring-boot/
          mvn -B package --file pom.xml
```
  - Guardar el archivo (hacemos commit directamente en GitHub por ejemplo) y ejecutamos manualmente el pipeline.
  - Explicar que realiza el pipeline anterior.

Lo que hice en este ejercicio, fue primero ubicarme en la carpeta donde tenia mi `spring-boot` que estaba en mi repo de github en `Ingenieria-de-SWiii/TP6/spring-boot`. Luego entre a la pestaña de **Actions**, ahi entre a la seccion de **CI** y ahi cree el workflow, el cual despues le hice un commit desde la interfaz de github.


El workflow utilizado fue el siguiente:

```yml
# This is a basic workflow to help you get started with Actions

name: CI

# Controls when the workflow will run
on:
  # Triggers the workflow on push or pull request events but only for the master branch
  push:
    paths:
    - 'TP6/spring-boot/**'
    branches: [ main ]
  pull_request:
    paths:
    - 'TP6/spring-boot/**'  
    branches: [ main ]

  # Allows you to run this workflow manually from the Actions tab
  workflow_dispatch:

# A workflow run is made up of one or more jobs that can run sequentially or in parallel
jobs:
  # This workflow contains a single job called "build"
  build:
    # The type of runner that the job will run on
    runs-on: ubuntu-latest

    # Steps represent a sequence of tasks that will be executed as part of the job
    steps:
      # Checks-out your repository under $GITHUB_WORKSPACE, so your job can access it
      - uses: actions/checkout@v2

      # Install Java JDK with maven
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
          cache: maven
          
      # Compile the application
      - name: Build with Maven
        run: |
          cd TP6/spring-boot/
          mvn -B package --file pom.xml
```

Como resultado obtuve, que se corrio de manera correcta.


![2](/TP8/img/2.png)


#### 3- Utilizando nuestros proyectos con Docker
  - Repetir el ejercicio 7 del trabajo práctico [trabajo práctico 7](07-servidor-build.md), pero utilizando GitHub Actions.
  - Generar `secretos` y los `pasos` necesarios para subir la imagen a Docker Hub. [Referencia](https://github.com/actions/starter-workflows/blob/main/ci/docker-publish.yml)

  Lo primero que hice fue crear los `secretos`, siguiendo estos pasos:

1. Cree una cuenta e inicie sesión en Docker Hub .
2. Vaya a Configuración de la cuenta => Seguridad: enlace y haga clic en Nuevo token de acceso .
3. Proporcione el nombre de su token de acceso, guárdelo y copie el valor (no podrá volver a verlo, deberá volver a generarlo).
4. Vaya a la configuración de sus secretos de GitHub (Configuración => Secretos, url https://github.com/{your_username}/{your_repository_name}/settings/secrets/actions ).
5. Cree dos secretos (no serán visibles para otros usuarios y se usarán en las compilaciones no bifurcadas) 
* DOCKERHUB_USERNAME : con el nombre de su cuenta de Docker Hub (no lo confunda con la cuenta de GitHub)
* DOCKERHUB_TOKEN - con el valor pegado de un token generado en el punto 3.

A la hora de crear secretos debo apretar el boton de `New repository secret` en la pestaña de Secretos.

![3](/TP8/img/3.png)

Archivo utilizado en GithubActions:

```yml
# This is a basic workflow to help you get started with Actions

name: build and push

# Controls when the workflow will run
on:
  # Triggers the workflow on push or pull request events but only for the master branch
  push:
    paths:
    - 'TP6/spring-boot/**'
    branches: [ main ]
  pull_request:
    paths:
    - 'TP6/spring-boot/**'  
    branches: [ main ]

  # Allows you to run this workflow manually from the Actions tab
  workflow_dispatch:

# A workflow run is made up of one or more jobs that can run sequentially or in parallel
jobs:
  # This workflow contains a single job called "build"
  build:
    # The type of runner that the job will run on
    runs-on: ubuntu-latest

    # Steps represent a sequence of tasks that will be executed as part of the job
    steps:
      # Checks-out your repository under $GITHUB_WORKSPACE, so your job can access it
      - uses: actions/checkout@v2

      # Install Java JDK with maven
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
          cache: maven
          
      # Compile the application
      - name: Build with Maven
        run: |
          cd TP6/spring-boot/
          mvn -B package --file pom.xml
  # define job to build and publish docker image
  build-and-push-docker-image:
    name: Build Docker image and push to repositories
    # run only when code is compiling and tests are passing
    runs-on: ubuntu-latest

    # steps to perform in job
    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      # setup Docker buld action
      - name: Set up Docker Buildx
        id: buildx
        uses: docker/setup-buildx-action@v2

      - name: Login to DockerHub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}
      
      - name: Build image and push to Docker Hub and GitHub Container Registry
        uses: docker/build-push-action@v2
        with:
          # relative path to the place where source code with Dockerfile is located
          context: ./TP6/spring-boot/
          # Note: tags has to be all lower-case
          tags: |
            fransappia01/springboot-gitactions:latest
          # build on feature branches, push only on main branch
          push: true

      - name: Image digest
        run: echo ${{ steps.docker_build.outputs.digest }}
```


En mi archivo yml tuve que cambiar la linea 77: `push: ${{ github.ref == 'refs/heads/main' }}` por `push: true` para que me envie la imagen a DockerHub. Luego anduvo correctamente.

![3.1](/TP8/img/3.1.png)

![3.2](/TP8/img/3.2.png)

Referencia para crear el archivo .yml: https://event-driven.io/en/how_to_buid_and_push_docker_image_with_github_actions/

#### 4- Opcional: Configurando CircleCI
  - De manera similar al ejercicio 2, configurar un build job para el mismo proyecto, pero utilizando CircleCI
  - Para capturar artefactos, utilizar esta referencia: https://circleci.com/docs/2.0/artifacts/
  - Como resultado de este ejercicio, subir el config.yml a la carpeta **spring-boot**

#### 5- Opcional: Configurando TravisCI
  - Configurar el mismo proyecto, pero para TravisCI. No es necesario publicar los artefactos porque TravisCI no dispone de esta funcionalidad.
  - Como resultado de este ejercicio subir el archivo .travis.yml a la carpeta **spring-boot**

#### 6- Opcional: Configurando Codefresh
  - Configurar el mismo proyecto, pero para Codefresh. 
  - Como resultado de este ejercicio subir el archivo codefresh.yml a la carpeta **spring-boot**

#### 7- Opcional: Configurando Gitlab
  - Configurar el mismo proyecto, pero para Gitlab. 
  - Como resultado de este ejercicio subir el archivo .gitlab-ci.yml a la carpeta **spring-boot**