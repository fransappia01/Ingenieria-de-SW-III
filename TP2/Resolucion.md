#### 1- Instalar Docker Community Edition 
  - Diferentes opciones para cada sistema operativo
  - https://docs.docker.com/
  - Ejecutar el siguiente comando para comprobar versiones de cliente y demonio.
```bash
docker version
```

![1](/TP2/img/1.png)

#### 2- Explorar DockerHub
   - Registrase en docker hub: https://hub.docker.com/
   - Familiarizarse con el portal

   ![2](/TP2/img/2.png)

   #### 3- Obtener la imagen BusyBox
  - Ejecutar el siguiente comando, para bajar una imagen de DockerHub
  ```bash
  docker pull busybox
  ```
![3.1](/TP2/img/3.1.png)

  - Verificar qué versión y tamaño tiene la imagen bajada, obtener una lista de imágenes locales:
```bash
docker images
```

  ![3.2](/TP2/img/3.2.png)

  #### 4- Ejecutando contenedores
  - Ejecutar un contenedor utilizando el comando **run** de docker:
```bash
docker run busybox
```
![4](/TP2/img/4.png)

  - Explicar porque no se obtuvo ningún resultado

  - Especificamos algún comando a correr dentro del contendor, ejecutar por ejemplo:
```bash
docker run busybox echo "Hola Mundo"
```
![4.1](/TP2/img/4.1.png)
  - Ver los contendores ejecutados utilizando el comando **ps**:
```bash
docker ps
```

  - Vemos que no existe nada en ejecución, correr entonces:
```bash
docker ps -a
```
  - Mostrar el resultado y explicar que se obtuvo como salida del comando anterior.

   ![4.2](/TP2/img/4.2.png)

   #### 5- Ejecutando en modo interactivo

  - Ejecutar el siguiente comando
```bash
docker run -it busybox sh
```
  - Para cada uno de los siguientes comandos dentro de contenedor, mostrar los resultados:
```bash
ps
uptime
free
ls -l /
```
  - Salimos del contendor con:
```bash
exit
```

![5](/TP2/img/5.png)

#### 6- Borrando contendores terminados

  - Obtener la lista de contendores 
```bash
docker ps -a
```
![6](/TP2/img/6.png)

  - Para borrar podemos utilizar el id o el nombre (autogenerado si no se especifica) de contendor que se desee, por ejemplo:
```bash
docker rm elated_lalande
```
  - Para borrar todos los contendores que no estén corriendo, ejecutar cualquiera de los siguientes comandos:
```bash
docker rm $(docker ps -a -q -f status=exited)
```
```bash
docker container prune
```
![6](/TP2/img/6.1.png)

#### 7- Montando volúmenes

Hasta este punto los contenedores ejecutados no tenían contacto con el exterior, ellos corrían en su propio entorno hasta que terminaran su ejecución. Ahora veremos cómo montar un volumen dentro del contenedor para visualizar por ejemplo archivos del sistema huésped:

  - Ejecutar el siguiente comando, cambiar myusuario por el usuario que corresponda. En linux/Mac puede utilizarse /home/miusuario):
```bash
docker run -it -v C:\Users\misuario\Desktop:/var/escritorio busybox /bin/sh
```
  - Dentro del contenedor correr
```bash
ls -l /var/escritorio
touch /var/escritorio/hola.txt
```
![7](/TP2/img/7.png)

  - Verificar que el Archivo se ha creado en el escritorio o en el directorio home según corresponda.


     ![7.1](/TP2/img/7.1.png)

     #### 8- Publicando puertos

En el caso de aplicaciones web o base de datos donde se interactúa con estas aplicaciones a través de un puerto al cual hay que acceder, estos puertos están visibles solo dentro del contenedor. Si queremos acceder desde el exterior deberemos exponerlos.

  - Ejecutar la siguiente imagen, en este caso utilizamos la bandera -d (detach) para que nos devuelva el control de la consola:

```bash
docker run -d daviey/nyan-cat-web
```
   ![8](/TP2/img/8.png)


  - Si ejecutamos un comando ps:
```bash
PS D:\> docker ps
CONTAINER ID        IMAGE                 COMMAND                  CREATED             STATUS              PORTS               NAMES
87d1c5f44809        daviey/nyan-cat-web   "nginx -g 'daemon of…"   2 minutes ago       Up 2 minutes        80/tcp, 443/tcp     compassionate_raman
```

   ![8.1](/TP2/img/8.1.png)

  - Vemos que el contendor expone 2 puertos el 80 y el 443, pero si intentamos en un navegador acceder a http://localhost no sucede nada.

  - Procedemos entonces a parar y remover este contenedor:
```bash
docker kill compassionate_raman
docker rm compassionate_raman
```
   ![8.2](/TP2/img/8.2.png)

  - Vamos a volver a correrlo otra vez, pero publicando uno de los puertos solamente, el este caso el 80

```bash
docker run -d -p 80:80 daviey/nyan-cat-web
```

   ![8.3](/TP2/img/8.33.png)

   Tuve que cambiar el puerto 80 al 777, ya que estaba en uso.

  - Accedamos nuevamente a http://localhost y expliquemos que sucede.

   ![8.4](/TP2/img/8.4.png)

Vemos la imagen corriendo en localhost:777.

#### 9- Utilizando una base de datos
- Levantar una base de datos PostgreSQL

```bash
mkdir $HOME/.postgres

docker run --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -v $HOME/.postgres:/var/lib/postgresql/data -p 5432:5432 -d postgres:9.4
```
![9](/TP2/img/9.png)

- Ejecutar sentencias utilizando esta instancia

```bash
docker exec -it my-postgres /bin/bash

psql -h localhost -U postgres

#Estos comandos se corren una vez conectados a la base

\l
create database test;
\connect test
create table tabla_a (mensaje varchar(50));
insert into tabla_a (mensaje) values('Hola mundo!');
select * from tabla_a;

\q

exit
```
![9.1](/TP2/img/9.1.png)
![9.2](/TP2/img/9.2.png)
- Conectarse a la base utilizando alguna IDE (Dbeaver - https://dbeaver.io/, eclipse, IntelliJ, etc...). Interactuar con los objectos objectos creados.

![9.5](/TP2/img/9.5.png)
![10](/TP2/img/10.png)
![10.1](/TP2/img/10.1.png)

- Explicar que se logro con el comando `docker run` y `docker exec` ejecutados en este ejercicio.


