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
![2.1](/TP10/img/2.1.png)


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

- Finalmente correr el test:
```npx codeceptjs run --steps```

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
- Para generar selectores fácilmente utilizamos plugins como (Firefox o Chrome)
  - TruePath https://addons.mozilla.org/en-US/firefox/addon/truepath/
  - ChroPath https://chrome.google.com/webstore/detail/chropath/ljngjbnaijcbncmcnjfhigebomdlkcjo
  
#### 3- Testeando la aplicación spring-boot
  - En un directorio, por ejemplo **.\proyectos\spring-boot-it** ejecutar:

```bash
npx create-codeceptjs .
```

 - Instalar CodeceptJS con la librería webdriverio
```npm install codeceptjs chai --save-dev```

 - Inicializar CodeceptJS: ```npx codeceptjs init```


- Responder las preguntas. Aceptar valores por defecto. Cuando pregunte por url colocar `http://localhost:8080` y y el nombre de los tests poner `spring-boot`


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
- Ejecutar los tests desde la carpeta `.\proyectos\spring-boot-it`

```
npx codeceptjs run --steps
```

- Analizar resultados

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
 
- La salida compatible con Jenkins esta en ./output/results.xml

#### 5- Integrar la ejecución en Jenkins
- Utilizando la funcionalidad de Junit test en Jenkins colectar estos resultados de la ejecución después del deployment.