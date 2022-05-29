FROM node

WORKDIR /usr/src/app
 
ENV PATH /usr/src/app/node_modules/.bin:$PATH

COPY ["package.json", "package-lock.json", "./"]

RUN npm install -g @angular/cli
RUN npm install --legacy-peer-deps

COPY . .
 
CMD ng serve --host 0.0.0.0

EXPOSE 4200/tcp