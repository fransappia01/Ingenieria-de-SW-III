# Desarrollo

#### 1- Instanciación del sistema
- Clonar el repositorio https://github.com/microservices-demo/microservices-demo
```bash
mkdir -p socks-demo
cd socks-demo
git clone https://github.com/microservices-demo/microservices-demo.git
```
- Ejecutar lo siguiente
```bash
cd microservices-demo
docker-compose -f deploy/docker-compose/docker-compose.yml up -d
```
- Una vez terminado el comando `docker-compose` acceder a http://localhost

A la hora de entrar a localhost, tener en cuenta que XAMPP en mi pc no este corriendo.

![1](/TP4/img/1.png)

- Generar un usuario

![1.1](/TP4/img/1.1.png)

- Realizar búsquedas por tipo de media, color, etc.
- Hacer una compra - poner datos falsos de tarjeta de crédito ;)

![1.2](/TP4/img/1.2.png)
#### 2- Investigación de los componentes
1. Describa los contenedores creados, indicando cuales son los puntos de ingreso del sistema

Se crearon estos 15 contenedores:
![2](/TP4/img/2.png)

- edge-router: punto de ingreso al sistema
- front-end: es la puerta de enlace de API. Los clientes llaman a esta API gateway, en lugar de llamar a los servicios directamente.

![2.2](/TP4/img/2.2.png)

La arquitectura es la siguiente:

![arq](/TP4/img/arqui.png)

Fuente: https://github.com/microservices-demo/microservices-demo/blob/master/internal-docs/design.md

2. Clonar algunos de los repositorios con el código de las aplicaciones
```bash
cd socks-demo
git clone https://github.com/microservices-demo/front-end.git
git clone https://github.com/microservices-demo/user.git
git clone https://github.com/microservices-demo/edge-router.git
.
.
```
3. ¿Por qué cree usted que se está utilizando repositorios separados para el código y/o la configuración del sistema? Explique puntos a favor y en contra.

Se utilizan repositorios separados porque cada microservicio es independiente uno del otro.

"Los microservicios son pequeños e independientes, y están acoplados de forma imprecisa. Los servicios pueden implementarse de manera independiente. "

### Beneficios

+ Cada microservicio puede evolucionar de manera independiente.
+ Mayor organizacion
+ Reutilizarse servicios para construuir otros sistemas

### Contras
- Mayor complejidad
- Todo se encuentra aislado

4. ¿Cuál contenedor hace las veces de API Gateway?

Es el contenedor `docker-compose_front-end_1`.

5. Cuando ejecuto este comando:
```bash
curl http://localhost/customers
```

![5](/TP4/img/5.png)
6. ¿Cuál de todos los servicios está procesando la operación?

La operacion es procesada por el servicio de *User*. Esto nos damos cuenta, ya que en esta operacion se muestran todos los datos del usuario.

7. ¿Y para los siguientes casos?
```bash
curl http://localhost/catalogue
curl http://localhost/tags
```

![6](/TP4/img/6.png)

![7](/TP4/img/7.png)

En estos casos,  la operacion es procesada por el servicio *Catalogue*, ya que la informacion que manejan es perteneciente al catalogo de medias.

8. ¿Como perisisten los datos los servicios?

A traves de bases de datos. Como vimos antes, algunos servicios vienen acompañados de un contenedor en el cual persisten sus datos.

*User* --> `docker-compose_user-db_1`

*Catalogue* --> `docker-compose_catalogue-db_1`

9. ¿Cuál es el componente encargado del procesamiento de la cola de mensajes?

El componente encargado de esto es `docker-compose_queue-master_1`. 

10. ¿Qué tipo de interfaz utilizan estos microservicios para comunicarse?

Todos los servicios se comunican usando REST sobre HTTP. Este fue elegido debido a la simplicidad del desarrollo y las pruebas. Sus especificaciones API están en desarrollo.

![100](/TP4/img/100.png)