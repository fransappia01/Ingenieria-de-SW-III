## 4- Desarrollo:

#### 1- Familiarizarse con CodeceptJs
  - El objeto **I** y sus funcionalidades básicas: https://codecept.io/basics

Cada prueba se describe dentro de una función `Scenario` con el objeto `I` que se le pasa. El objeto `I` es un actor , una abstracción para un usuario de prueba. El `I` es un objeto proxy para los ayudantes habilitados actualmente.

#Arquitectura

#### 2- Testeando la página de GitHub

- Instalar NodeJs v12 o superior: https://nodejs.org/en/download/

![2](/TP10/img/2.png/)

- En un directorio, por ejemplo **.\proyectos\ut** ejecutar:

```bash
npx create-codeceptjs .
```
![2.0](/TP10/img/2.1.png)


- Si esta utilizando codeceptjs 3.0.0, hay que actualizar a uno superior, por ejemplo 3.0.1
- Cambiar en packages.json `"codeceptjs": "^3.0.0",` por `"codeceptjs": "^3.0.1",` y ejecutar `npm install`
- Ininicializar un nuevo proyecto CodeceptJS:
```bash
npx codeceptjs init
```
- Elegimos las opciones por defecto, ponemos **github** cuando se nos pregunte por el nombre del primer test:
```powershell
D:\repos\ucc\ing-soft-3-2020\proyectos\ut>npx codeceptjs init

  Welcome to CodeceptJS initialization tool
  It will prepare and configure a test environment for you

Installing to D:\repos\ucc\ing-soft-3-2020\proyectos\ut
? Where are your tests located? ./*_test.js
? What helpers do you want to use? (Use arrow keys)
> Playwright
  WebDriver
  Puppeteer
  TestCafe
  Protractor
  Nightmare
  Appium
? Where should logs, screenshots, and reports to be stored? ./output
? Do you want localization for tests? (See https://codecept.io/translation/) English (no localization)
Configure helpers...
? [Playwright] Base url of site to be tested http://localhost
? [Playwright] Show browser window Yes
? [Playwright] Browser in which testing will be performed. Possible options: chromium, firefox or webkit chromium

Steps file created at ./steps_file.js
Config created at D:\repos\ucc\ing-soft-3-2020\proyectos\ut\codecept.conf.js
Directory for temporary output files created at './output'
Intellisense enabled in D:\repos\ucc\ing-soft-3-2020\proyectos\ut\jsconfig.json
TypeScript Definitions provide autocompletion in Visual Studio Code and other IDEs
Definitions were generated in steps.d.ts

 Almost ready... Next step:
Creating a new test...
----------------------
? Feature which is being tested (ex: account, login, etc) github
? Filename of a test github_test.js

Test for github_test.js was created in D:\repos\ucc\ing-soft-3-2020\proyectos\ut\github_test.js

--
CodeceptJS Installed! Enjoy supercharged testing! �
Find more information at https://codecept.io
```

Comando utilizado en powershell:

```powershell

PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10> npx codeceptjs init

  Welcome to CodeceptJS initialization tool
  It will prepare and configure a test environment for you

Installing to C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10
? Do you plan to write tests in TypeScript? Yes
? Where are your tests located? ./*_test.js
? What helpers do you want to use? Playwright
? Where should logs, screenshots, and reports to be stored? ./output
? Do you want localization for tests? (See https://codecept.io/translation/) English (no localization)
Configure helpers...
? [Playwright] Base url of site to be tested http://localhost
? [Playwright] Show browser window Yes
? [Playwright] Browser in which testing will be performed. Possible options: chromium, firefox, webkit or electron chromium

Config created at C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\codecept.conf.ts
Directory for temporary output files created at './output'
Installing packages:  typescript, ts-node, @types/node
+ ts-node@10.9.1
+ @types/node@18.8.5
+ typescript@4.8.4
added 18 packages from 53 contributors, updated 1 package and audited 482 packages in 21.35s

79 packages are looking for funding
  run `npm fund` for details

found 1 moderate severity vulnerability
  run `npm audit fix` to fix them, or `npm audit` for details
context
TypeScript Definitions provide autocompletion in Visual Studio Code and other IDEs
Definitions were generated in steps.d.ts

 Almost ready... Next step:
Creating a new test...
----------------------
? Feature which is being tested (ex: account, login, etc) github
? Filename of a test github_test.js

Test for github_test.ts was created in C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\github_test.ts

--
CodeceptJS Installed! Enjoy supercharged testing! 🤩
Find more information at https://codecept.io
```


- Editar el archivo generado:
```
Feature('My First Test');
Scenario('test something', (I) => {

});
```

- Escribir un escenario de prueba:
```Feature('My First Test');

Scenario('test something', (I) => {
  I.amOnPage('https://github.com');
  I.see('GitHub');
});
```
Debo poner todas las lineas juntas abajo una de la otra SIN ESPACIOS (sino no anda).

- Finalmente correr el test:
```npx codeceptjs run --steps```

![2.4](/TP10/img/2.4.png)

- Agregamos otras validaciones
```javascript
Scenario('test something', ({ I }) => {
    I.amOnPage('https://github.com');
    I.see('GitHub');
    I.see('The home for all developers')
    I.scrollPageToBottom()
    I.seeElement("//li[contains(.,'© 2022 GitHub, Inc.')]")
});
```

![2.5](/TP10/img/2.5.png)

- Para generar selectores fácilmente utilizamos plugins como (Firefox o Chrome)
  - TruePath https://addons.mozilla.org/en-US/firefox/addon/truepath/
  - ChroPath https://chrome.google.com/webstore/detail/chropath/ljngjbnaijcbncmcnjfhigebomdlkcjo

 ![2.6](/TP10/img/2.6.png) 
  
#### 3- Testeando la aplicación spring-boot
  - En un directorio, por ejemplo **.\proyectos\spring-boot-it** ejecutar:

```bash
npx create-codeceptjs .
```

 - Instalar CodeceptJS con la librería webdriverio
```npm install codeceptjs chai --save-dev```

 - Inicializar CodeceptJS: ```npx codeceptjs init```


- Responder las preguntas. Aceptar valores por defecto. Cuando pregunte por url colocar `http://localhost:8080` y y el nombre de los tests poner `spring-boot`



```powershell
PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it> npx create-codeceptjs .
npx: installed 32 in 11.214s


 ╔═╗ ╦═╗ ╔═╗ ╔═╗ ╔╦╗ ╔═╗
 ║   ╠╦╝ ║╣  ╠═╣  ║  ║╣
 ╚═╝ ╩╚═ ╚═╝ ╩ ╩  ╩  ╚═╝

 ╔═╗ ╔═╗ ╔╦╗ ╔═╗ ╔═╗ ╔═╗ ╔═╗ ╔╦╗  ╦ ╔═╗
 ║   ║ ║  ║║ ║╣  ║   ║╣  ╠═╝  ║   ║ ╚═╗
 ╚═╝ ╚═╝ ═╩╝ ╚═╝ ╚═╝ ╚═╝ ╩    ╩  ╚╝ ╚═╝


 🔌 Supercharged End 2 End Testing 🌟

Creating CodeceptJS project in C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it

Powered by Playwright engine
package.json file does not exist in current dir, creating it...
Installing packages:  codeceptjs@3, @codeceptjs/ui, @codeceptjs/examples, @codeceptjs/configure, playwright@1

> playwright@1.27.1 install C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\node_modules\playwright
> node install.js


> core-js@2.6.12 postinstall C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\node_modules\core-js
> node -e "try{require('./postinstall')}catch(e){}"


> electron@15.5.7 postinstall C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\node_modules\electron
> node install.js

+ @codeceptjs/ui@0.4.7
+ codeceptjs@3.3.6
+ @codeceptjs/examples@1.2.1
+ @codeceptjs/configure@0.10.0
+ playwright@1.27.1
added 462 packages from 293 contributors and audited 464 packages in 150.02s

79 packages are looking for funding
  run `npm fund` for details

found 1 moderate severity vulnerability
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

PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it> npm install codeceptjs chai --save-dev
npm WARN deprecated cucumber-expressions@6.6.2: This package is now published under @cucumber/cucumber-expressions
npm WARN deprecated gherkin@5.1.0: This package is now published under @cucumber/gherkin
npm WARN deprecated debug@4.1.1: Debug versions >=3.2.0 <3.2.7 || >=4 <4.3.1 have a low-severity ReDos regression when used in a Node.js environment. It is recommended you upgrade to 3.2.7 or 4.3.1. (https://github.com/visionmedia/debug/issues/797)
npm WARN deprecated fsevents@2.1.3: "Please update to latest v2.3 or v2.2"
npm WARN optional SKIPPING OPTIONAL DEPENDENCY: fsevents@~2.1.2 (node_modules\mocha\node_modules\chokidar\node_modules\fsevents):
npm WARN notsup SKIPPING OPTIONAL DEPENDENCY: Unsupported platform for fsevents@2.1.3: wanted {"os":"darwin","arch":"any"} (current: {"os":"win32","arch":"x64"})
npm WARN codeceptjs@3.3.6 requires a peer of @faker-js/faker@^5.5.3 but none is installed. You must install peer dependencies yourself.
npm WARN optional SKIPPING OPTIONAL DEPENDENCY: fsevents@2.3.2 (node_modules\fsevents):
npm WARN notsup SKIPPING OPTIONAL DEPENDENCY: Unsupported platform for fsevents@2.3.2: wanted {"os":"darwin","arch":"any"} (current: {"os":"win32","arch":"x64"})

+ codeceptjs@3.3.6
+ chai@4.3.6
updated 2 packages and audited 465 packages in 22.707s

32 packages are looking for funding
  run `npm fund` for details

found 1 moderate severity vulnerability
  run `npm audit fix` to fix them, or `npm audit` for details
PS C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it> npx codeceptjs init

  Welcome to CodeceptJS initialization tool
  It will prepare and configure a test environment for you

Installing to C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it
? Do you plan to write tests in TypeScript? Yes
? Where are your tests located? ./*_test.js
? What helpers do you want to use? Playwright
? Where should logs, screenshots, and reports to be stored? ./output
? Do you want localization for tests? (See https://codecept.io/translation/) English (no localization)
Configure helpers...
? [Playwright] Base url of site to be tested http://localhost:8080
? [Playwright] Show browser window Yes
? [Playwright] Browser in which testing will be performed. Possible options: chromium
, firefox, webkit or electron chromium

Config created at C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\codecept.conf.ts
Directory for temporary output files created at './output'
Installing packages:  typescript, ts-node, @types/node
+ ts-node@10.9.1
+ @types/node@18.11.0
+ typescript@4.8.4
added 18 packages from 53 contributors, updated 1 package and audited 482 packages in 19.962s

79 packages are looking for funding
  run `npm fund` for details

found 1 moderate severity vulnerability
  run `npm audit fix` to fix them, or `npm audit` for details
context
TypeScript Definitions provide autocompletion in Visual Studio Code and other IDEs
Definitions were generated in steps.d.ts

 Almost ready... Next step:
Creating a new test...
----------------------
? Feature which is being tested (ex: account, login, etc) spring-boot
? Filename of a test spring-boot_tests.js

Test for spring-boot_tests.js was created in C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\spring-boot_tests.js

--
CodeceptJS Installed! Enjoy supercharged testing! 🤩
Find more information at https://codecept.io
```

- Editar el archivo generado `spring-boot_tests.js`:
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

- Reemplazar la sección helpers de codecept.conf.js por:

```javascript
	helpers: {
		REST: {
			endpoint: "http://localhost:8080",
			onRequest: () => {
			}
		}
	}
```

- Levantar la aplicación spring-boot en otra consola (usando java o Docker):
```bash
cd ./proyectos/spring-boot
java -jar target/spring-boot-sample-actuator-2.0.2.jar
```
Levante la aplicacion de spring-boot tirando el comando `docker run docker run -p 8080:8080 test-spring-boot`, sino esta creado tirar docker build (Comandos aca: https://github.com/fernandobono/ing-software-3/blob/master/trabajos/06-construccion-imagenes-docker.md)

- Ejecutar los tests desde la carpeta `.\proyectos\spring-boot-it`

```
npx codeceptjs run --steps
```

- Analizar resultados

![3](/TP10/img/3.png)


#### 4- Habilitar reportes para utilizarlos en CICD
- Instalar el módulo para reporting
```bash
npm i mocha-junit-reporter mocha-multi --save
```
- Reemplazar la key mocha en el archivo codecept.conf.js por:

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

- Ejecutar los tests nuevamente
```bash
npx codeceptjs run --steps --reporter mocha-multi
```
 ![4](/TP10/img/4.png)

- La salida compatible con Jenkins esta en ./output/results.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<testsuites name="Mocha Tests" time="0.289" tests="2" failures="0">
  <testsuite name="Root Suite" timestamp="2022-10-16T00:12:02" tests="0" time="0.000" failures="0">
  </testsuite>
  <testsuite name="spring-boot" timestamp="2022-10-16T00:12:02" tests="2" file="C:\Users\Francisco\Documents\FRAN\----------CUARTO AÑO---------\Segundo trimestre\Ingenieria-de-SW-III\TP10\proyecto\spring-boot-it\spring-boot_test.js" time="0.264" failures="0">
    <testcase name="spring-boot: Verify a successful call" time="0.170" classname="Verify a successful call">
    </testcase>
    <testcase name="spring-boot: Verify return value" time="0.050" classname="Verify return value">
    </testcase>
  </testsuite>
</testsuites>
```

#### 5- Integrar la ejecución en Jenkins
- Utilizando la funcionalidad de Junit test en Jenkins colectar estos resultados de la ejecución después del deployment.

![5](/TP10/img/5.png)

![5.1](/TP10/img/5.1.png)

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
                // Get some code from a GitHub repository
                git 'https://github.com/fransappia01/Ingenieria-de-SW-III'
                
                dir('spring-boot'){
                    sh("mvn package")
                }

            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    dir('spring-boot'){
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        }
    stage('Run Tests'){
        steps{
            dir ('TP10/proyecto/spring-boot-it/output'){
                
                junit 'result.xml'
            }
        }
    }
}
}
```