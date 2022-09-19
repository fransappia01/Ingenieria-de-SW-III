#especificamos que imagen usar
FROM node:13.12.0-alpine AS dependencias
#copiamos el json
COPY package.json /tmp/
#especificamos directorio de trabajo
WORKDIR /tmp/
#ejecuto el npm install durante el build
RUN npm install

FROM dependencias
#copiamos de la imagen anterior los ejecutables
COPY --from=DEPENDENCIAS /tmp/node_modules ./node_modules
#copiamos todo lo de la carpeta, sin esto no funciona
COPY . .
#exponemos el puerto que vamos a usar
EXPOSE 3000
CMD npm run start

