FROM node:18

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm install

COPY . .

RUN npm run build --prod

EXPOSE 4200

CMD [ "npm", "start" ]