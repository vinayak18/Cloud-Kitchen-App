FROM node:20

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm install

COPY . .

RUN npm run build --prod

EXPOSE 4201

CMD [ "npm", "start" ]