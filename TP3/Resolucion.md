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

![4](/TP3/img/4.png)

- Una vez terminado acceder a http://localhost:5000/ y http://localhost:5001
- Emitir un voto y ver el resultado en tiempo real.
- Para emitir más votos, abrir varios navegadores diferentes para poder hacerlo

Puerto 5000:

![4.1](/TP3/img/4.1.png)

Puerto 5001:

![4.2](/TP3/img/4.2.png)

- Explicar como está configurado el sistema, puertos, volumenes componenetes involucrados, utilizar el Docker compose como guía.

Se crearon cinco contenedores:

![4.3](/TP3/img/4.3.png)

Tenemos dos redes 'example-voting-app_back-tier' y 'example-voting-app_front-tier'.
Los 5 contenedores forman parte de la red back pero solo dos contenedores forman parte de la red front (vote y result). Estan publicados en los puertoss 5000 y 5001 respectivamente. Lo vemos a continuacion y tambien en el archivo .yml.

```bash
PS C:\Users\Francisco\Desktop\example-voting-app> docker network inspect example-voting-app_back-tier
[
    {
        "Name": "example-voting-app_back-tier",
        "Id": "28128162a1be9aaee5053a7945e7033c5d525eb6595dafdb73bf4143d624ffa3",
        "Created": "2022-08-25T17:26:00.588503531Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.22.0.0/16",
                    "Gateway": "172.22.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": true,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "18a2382c6b09f87ca12057d60602facd76b9380c646087d8d7274b62eaa24078": {
                "Name": "db",
                "EndpointID": "1d126ea68dff2fa1dd5c3f9d6d59e3f350dd137f8969d5a30ba08d95a5d3797f",
                "MacAddress": "02:42:ac:16:00:04",
                "IPv4Address": "172.22.0.4/16",
                "IPv6Address": ""
            },
            "2b9f6f4ec10a81ab7b8fb4ca78411961823724584c1a337379819ddd6eafee63": {
                "Name": "example-voting-app_worker_1",
                "EndpointID": "fa3f42a72703f2dc2441a7a9edbd3281178c48a3bf65361c1460251f20954b0d",
                "MacAddress": "02:42:ac:16:00:02",
                "IPv4Address": "172.22.0.2/16",
                "IPv6Address": ""
            },
            "7851dcd50abd1ab38ae06861c1758f6f6843e9a2cd7c57c5a3c45c6254de85e5": {
                "Name": "example-voting-app_vote_1",
                "EndpointID": "811b98cf059d096b72b98a38a6bc46fee53f1cd9b8502252033367331e210739",
                "MacAddress": "02:42:ac:16:00:05",
                "IPv4Address": "172.22.0.5/16",
                "IPv6Address": ""
            },
            "79bcdbb9963f6596599b231454f0124fad4b833ef6970a7fbc27aca35af03209": {
                "Name": "example-voting-app_result_1",
                "EndpointID": "f01ce06d7040a0ceaeaef4809440fd4eb27e8bb5235d22a83cb41973b470e115",
                "MacAddress": "02:42:ac:16:00:06",
                "IPv4Address": "172.22.0.6/16",
                "IPv6Address": ""
            },
            "7e9a2fba92f63a469a57349ffb02912afc2119810b66291d8115e88573a7271d": {
                "Name": "redis",
                "EndpointID": "ab4ef907473e819d2da7eb3878689e744e1863fe6e3d9d017cf9d68d2919765e",
                "MacAddress": "02:42:ac:16:00:03",
                "IPv4Address": "172.22.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {
            "com.docker.compose.network": "back-tier",
            "com.docker.compose.project": "example-voting-app",
            "com.docker.compose.version": "1.29.2"
        }
    }
]

PS C:\Users\Francisco\Desktop\example-voting-app> docker network inspect example-voting-app_front-tier
[
    {
        "Name": "example-voting-app_front-tier",
        "Id": "e9385b66aa1052d9731bed3e06e2732080ea7b423ffaa638172c4b7074eb778f",
        "Created": "2022-08-25T17:26:00.451924449Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.21.0.0/16",
                    "Gateway": "172.21.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": true,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "7851dcd50abd1ab38ae06861c1758f6f6843e9a2cd7c57c5a3c45c6254de85e5": {
                "Name": "example-voting-app_vote_1",
                "EndpointID": "a2d1f61cffeb82167b26b1c62ca98dd480cfd8b74362b8fcc5432ba608319461",
                "MacAddress": "02:42:ac:15:00:03",
                "IPv4Address": "172.21.0.3/16",
                "IPv6Address": ""
            },
            "79bcdbb9963f6596599b231454f0124fad4b833ef6970a7fbc27aca35af03209": {
                "Name": "example-voting-app_result_1",
                "EndpointID": "0ed6d18f89508a4d07d43f6c905ef497b47fbbbdf43266aa9dcf9b2afa19529d",
                "MacAddress": "02:42:ac:15:00:02",
                "IPv4Address": "172.21.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {
            "com.docker.compose.network": "front-tier",
            "com.docker.compose.project": "example-voting-app",
            "com.docker.compose.version": "1.29.2"
        }
    }
]
```

#### 5- Análisis detallado
- Exponer más puertos para ver la configuración de Redis, y las tablas de PostgreSQL con alguna IDE como dbeaver.

En esta parte tuve que cambiar el archivo 'docker-compose-javaworker.yml' donde agregamos los puertos 5432 para poderlo conectar a la base de datos.

![4.4](/TP3/img/4.4.png)

![4.5](/TP3/img/4.5.png)

- Revisar el código de la aplicación Python `example-voting-app\vote\app.py` para ver como envía votos a Redis.
- Revisar el código del worker `example-voting-app\worker\src\main\java\worker\Worker.java` para entender como procesa los datos.
- Revisar el código de la aplicacion que muestra los resultados `example-voting-app\result\server.js` para entender como muestra los valores.
- Escribir un documento de arquitectura sencillo, pero con un nivel de detalle moderado, que incluya algunos diagramas de bloques, de sequencia, etc y descripciones de los distintos componentes involucrados es este sistema y como interactuan entre sí.

### Presentación del trabajo práctico 3

La presentación de este práctico forma parte del trabajo integrador, especialemente el último punto con el analisis del sistema, todos los documentos e imagenes pueden ser subidos a una carpeta trabajo-practico-03 con las salidas de los comandos utilizados, explicaciones y respuestas a las preguntas.