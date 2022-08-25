## Desarrollo
------------------------------------------

#### 1- Sistema distribuido simple 
  - Ejecutar el siguiente comando para crear una red en docker
  ```bash
  docker network create -d bridge mybridge
  ```
  - Instanciar una base de datos Redis conectada a esa Red.
  ```bash
   docker run -d --net mybridge --name db redis:alpine
   ```
  - Levantar una aplicacion web, que utilice esta base de datos
  ```bash
    docker run -d --net mybridge -e REDIS_HOST=db -e REDIS_PORT=6379 -p 5000:5000 --name web alexisfr/flask-app:latest
  ```

![1](/TP3/img/1.png)

  - Abrir un navegador y acceder a la URL: http://localhost:5000/

![1.1](/TP3/img/1.1.png)
  
  - Verificar el estado de los contenedores y redes en Docker, describir:
    - ¿Cuáles puertos están abiertos?

![1.2](/TP3/img/1.2.png)
    - Mostrar detalles de la red `mybridge` con Docker.

![1.3](/TP3/img/1.3.png)

- ¿Qué comandos utilizó?

Los comandos que utilize fueron "docker ps" para ver los puertos abiertos, y "docker network inspect mybridge" para mostrar detalles de la red mybridge.

#### 2- Análisis del sistema 
  - Siendo el código de la aplicación web el siguiente:
```python
import os

from flask import Flask
from redis import Redis


app = Flask(__name__)
redis = Redis(host=os.environ['REDIS_HOST'], port=os.environ['REDIS_PORT'])
bind_port = int(os.environ['BIND_PORT'])


@app.route('/')
def hello():
    redis.incr('hits')
    total_hits = redis.get('hits').decode()
    return f'Hello from Redis! I have been seen {total_hits} times.'


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True, port=bind_port)
```
  - Explicar cómo funciona el sistema

Basicamente es una funcion realizada en Python que tiene un contador que va aumentando cada vez que actualizamos la pagina (la que acccedimos antes en localhost).

DESPUES COMENTAR LINEA POR LINEA

  - ¿Para qué se sirven y porque están los parámetros `-e` en el segundo Docker run del ejercicio 1?

ver

  - ¿Qué pasa si ejecuta `docker rm -f web` y vuelve a correr ` docker run -d --net mybridge -e REDIS_HOST=db -e REDIS_PORT=6379 -p 5000:5000 --name web alexisfr/flask-app:latest` ?

![2.2](/TP3/img/2.2.png)

Ejecutamos `docker rm -f web`:

![2](/TP3/img/2.png)

Luego ejecutamos ` docker run -d --net mybridge -e REDIS_HOST=db -e REDIS_PORT=6379 -p 5000:5000 --name web alexisfr/flask-app:latest`:

![2.1](/TP3/img/2.1.png)

Basicamente lo que ocurre, es que estamos borrando el contenedor en ejecucion que contiene a la pagina web. Por lo tanto no podremos acceder a la pagina. 

Luego, al ejecutar el segundo comando, volvemos a acceder a la pagina web y el contador de Hits (veces que entro a la pagina) mantiene su cuenta. Es decir, se guardan los datos.

Esto ocurre debido a que los datos del contador estan guradados en la base de datos, la cual no se eliminó.

  - ¿Qué occure en la página web cuando borro el contenedor de Redis con `docker rm -f db`?

![2.2](/TP3/img/2.5.png)

Podemos acceder a la  pagina web pero nos sale el siguiente error:

![2.3](/TP3/img/2.3.png)



  - Y si lo levanto nuevamente con `docker run -d --net mybridge --name db redis:alpine` ?

Accedemos a la pagina web pero esta vez se reinició el contador.

![2.4](/TP3/img/2.4.png)

  - ¿Qué considera usted que haría falta para no perder la cuenta de las visitas?
  - Para eliminar los elementos creados corremos:
  ```bash
  docker rm -f db
  docker rm -f web
  docker network rm mybridge
  ```
  
#### 3- Utilizando docker compose 
  - Normalmente viene como parte de la solucion cuando se instaló Docker
  - De ser necesario instalarlo hay que ejecutar:
  ```bash
  sudo pip install docker-compose
  ```
  - Crear el siguente archivo `docker-compose.yaml` en un directorio de trabajo:

```yaml
version: '3.6'
services:
  app:
    image: alexisfr/flask-app:latest
    depends_on:
      - db
    environment:
      - REDIS_HOST=db
      - REDIS_PORT=6379
    ports:
      - "5000:5000"
  db:
    image: redis:alpine
    volumes:
      - redis_data:/data
volumes:
  redis_data:
```

  - Ejecutar `docker-compose up -d`

![3](/TP3/img/3.png)

En esta parte hay que tener cuenta que debo estar parado sobre la carpeta donde se encuentra el archivo .yml. Tambien tener cuidado con las sangrias en ese archivo (sino me da error).

  - Acceder a la url http://localhost:5000/

![3.1](/TP3/img/3.1.png)

  - Ejecutar `docker ps`, `docker network ls` y `docker volume ls`

![3.2](/TP3/img/3.2.png)


  - ¿Qué hizo **Docker Compose** por nosotros? Explicar con detalle.

Basicamente el docker-compose realizó todos los pasos que realizamos en el punto anterior basandose en el archivo .yml. Es decir, creó (y puso en ejecución) los contenedores `tp3_app_1` (equivalente a web) y `tp3_db_1` (equivalente a db), creo una red del tipo bridge denominada `tp3_default` (equivalente a mybridge) que conecta la app web con la base de datos y por último asignó un volumen denominado `tp3_redis_data`.

  - Desde el directorio donde se encuentra el archivo `docker-compose.yaml` ejecutar:
  ```bash
  docker-compose down
  ```

  ![3.3](/TP3/img/3.3.png)
 
#### 4- Aumentando la complejidad, análisis de otro sistema distribuido.
Este es un sistema compuesto por:

- Una aplicación web de Python que te permite votar entre dos opciones
- Una cola de Redis que recolecta nuevos votos
- Un trabajador .NET o Java que consume votos y los almacena en...
- Una base de datos de Postgres respaldada por un volumen de Docker
- Una aplicación web Node.js que muestra los resultados de la votación en tiempo real.

Pasos:
- Clonar el repositorio https://github.com/dockersamples/example-voting-app
- Abrir una línea de comandos y ejecutar
```bash
cd example-voting-app
docker-compose -f docker-compose-javaworker.yml up -d
```
- Una vez terminado acceder a http://localhost:5000/ y http://localhost:5001
- Emitir un voto y ver el resultado en tiempo real.
- Para emitir más votos, abrir varios navegadores diferentes para poder hacerlo
- Explicar como está configurado el sistema, puertos, volumenes componenetes involucrados, utilizar el Docker compose como guía.

#### 5- Análisis detallado
- Exponer más puertos para ver la configuración de Redis, y las tablas de PostgreSQL con alguna IDE como dbeaver.
- Revisar el código de la aplicación Python `example-voting-app\vote\app.py` para ver como envía votos a Redis.
- Revisar el código del worker `example-voting-app\worker\src\main\java\worker\Worker.java` para entender como procesa los datos.
- Revisar el código de la aplicacion que muestra los resultados `example-voting-app\result\server.js` para entender como muestra los valores.
- Escribir un documento de arquitectura sencillo, pero con un nivel de detalle moderado, que incluya algunos diagramas de bloques, de sequencia, etc y descripciones de los distintos componentes involucrados es este sistema y como interactuan entre sí.

### Presentación del trabajo práctico 3

La presentación de este práctico forma parte del trabajo integrador, especialemente el último punto con el analisis del sistema, todos los documentos e imagenes pueden ser subidos a una carpeta trabajo-practico-03 con las salidas de los comandos utilizados, explicaciones y respuestas a las preguntas.