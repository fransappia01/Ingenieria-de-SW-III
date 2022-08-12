# Resolucion del Trabajo Practico 1

## 1) Instalar Git
Los pasos y referencias asumen el uso del sistema operativo Windows, en caso otros SO seguir recomendaciones específicas.

* Bajar e instalar el cliente git. Por ejemplo, desde https://git-scm.com/
* Bajar e instalar un cliente visual. Por ejemplo, TortoiseGit para Windows o SourceTree para Windows/MAC: https://tortoisegit.org/ https://www.sourcetreeapp.com/ Lista completa: https://git-scm.com/downloads/guis/

                        Hecho✔️

## 2) Crear un repositorio local y agregar archivos
* Crear un repositorio local en un nuevo directorio.
* Agregar un archivo Readme.md, agregar algunas líneas con texto a dicho archivo.
* Crear un commit y proveer un mensaje descriptivo.

                        Hecho✔️

## 3) Crear un repositorio remoto
* Crear una cuenta en https://github.com
* Crear un nuevo repositorio en dicha página (vacío)
* Asociar el repositorio local creado en el punto 2 al creado en github.
* Subir los cambios locales a github.

                        Hecho✔️

## 4) Familiarizarse con el concepto de Pull Request
* Para algunos de los puntos proveer imágenes.
* Explicar que es un pull request.
* Crear un branch local y agregar cambios a dicho branch.
* Subir el cambio a dicho branch y crear un pull request.
* Completar el proceso de revisión en github y mergear el PR al branch master.

    -   ![4](/TP1/img/4.png)

### Que es un pull request? 
Un pull request es una petición que el propietario de un fork de un repositorio hace al propietario del repositorio original para que este último incorpore los commits que están en el fork. Es la forma de contribuir a un proyecto grupal o de código abierto.

## 5) Mergear código con conflictos
* Clonar en un segundo directorio el repositorio creado en github.
* En el clon inicial, modificar el Readme.md agregando más texto.
Hacer commit y subir el cambio a master a github.
* En el segundo clon también agregar texto, en las mismas líneas que se modificaron el punto anterior. Intentar subir el cambio, haciendo un commit y push. Mostrar el error que se obtiene.

![5.3](/TP1/img/5.3.png)
![5.4](/TP1/img/5.4.png)


* Hacer pull y mergear el código (solo texto por ahora), mostrar la herramienta de mergeo como luce.
    -     El Visual Studio Code nos muestra los errores que causan el conflicto
![5.5](/TP1/img/5.5.png)
* Resolver los conflictos del código.

    -     Para revertir esta situacion, debemos borrar los caracteres que aparecen y solucionar el conflicto.

* Explicar las versiones LOCAL, BASE y REMOTE.

    -     Conforman el “three-way merge”, donde base es el antecesor (de donde partieron los cambios que luego colisionaron), remote es una versión que contiene los cambios que se le aplicaron a base y se subieron (push) al repositorio remoto; local es la versión que contiene los cambios que se le aplicaron a base pero se encuentran en el directorio local.


* Pushear el cambio mergeado.


## 6) Algunos ejercicios online
• Entrar a la página https://learngitbranching.js.org/

• Completar los ejercicios Introduction Sequence

• Opcional - Completar el resto de los ejercicios para ser un experto en Git!!!







