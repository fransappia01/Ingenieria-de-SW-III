# Documento de arquitectura

## Consigna

- Escribir un documento de arquitectura sencillo, pero con un nivel de detalle moderado, que incluya algunos diagramas de bloques, de sequencia, etc y descripciones de los distintos componentes involucrados es este sistema y como interactuan entre sí.

## Desarrollo

En primer lugar, vemos la arquitectura principal de la aplicacion web en la cual trabajamos. Esta app web consta de 5 sistemas:

* Una aplicación web front-end en Python o ASP.NET Core que le permite votar entre dos opciones
* Una cola Redis o NATS que recopila nuevos votos
* Un worker de .NET Core , Java o .NET Core 2.1 que consume votos y los almacena en...
* Una base de datos Postgres o TiDB respaldada por un volumen Docker
* Una aplicación web Node.js o ASP.NET Core SignalR que muestra los resultados de la votación en tiempo real

![arquitecture](/TP3/img/architecture.png)

En base a esto, se realizó un diagrama de secuencia para ver con mayor claridad como funciona cada uno de estos sistemas:

