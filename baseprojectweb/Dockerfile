FROM node
WORKDIR /app
COPY package.json ./
RUN npm install -g @angular/cli
RUN npm install @angular/cdk
RUN npm install
COPY . .
EXPOSE 4200
CMD npm run start