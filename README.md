# Cena - Menu generation

This project contains all the sources for the cena's menu generation.

## [TL;DR]

Considering you have installed the [prerequesite](#general-prerequisites) tools `Make`, `Docker`, `Java`, `Maven`, follow these steps to get started:

- To build the project, from the root folder, run:
`make`

- To start up the application and its dependencies, run:
`make up`

- You can access the API documentation page at the following address: [`http://localhost:8080/api/docs/api-guide.html`](http://localhost:8080/api/docs/api-guide.html)

## Project resources

- [Github](https://github.com/adhuc-projects/cena)
- [Travis CI](https://travis-ci.org/adhuc-projects/cena)
- [Codacy](https://www.codacy.com/app/adhuc-projects/cena/dashboard)

## General prerequisites

### Tools installation

- [Git](http://help.github.com/set-up-git-redirect)
- [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/install/)
- [NodeJS](https://nodejs.org/) (6.9.x or later) and [NPM](https://www.npmjs.com/) (3.x.x or later)
- [Angular CLI](https://cli.angular.io/)

Be sure that your `JAVA_HOME` environment variable points to the `jdk1.8.0` folder extracted from the JDK download.

### Makefile

The following sections propose to use `Makefile` targets in the form of`make` commands to abstract development lifecycle goals from technologies (mvn, npm,...) and practical details of implementation (locations, profiles,...).

`make` is available by default on unix based systems, and can be installed on Windows from this [site](http://gnuwin32.sourceforge.net/packages/make.htm).

## Project structure

The project is actually composed of the [Angular](https://angular.io/) front-end Single Page Application and the backend application, both served through a [Spring-Boot](https://projects.spring.io/spring-boot/) application.

Its sources folders are composed of :

- `src/main/java`, containing the Java sources for the backend part
- `src/main/angular`, containing the Typescript sources for the front-end part

## Usage

This project is designed to be usable in both development situation and in classical maven build.

### Development Usage

#### Angular SPA

The Angular Single Page Application can be run directly and independently using [Angular CLI](https://cli.angular.io/). It will provide a stand-alone application, that is still connected to the backend application. The main advantage of running the SPA using Angular CLI is that its source code is re-transpiled on each change, making the development process more fluid.

From `cena` project folder, execute either `make run-angular` or, from `src/main/angular` folder, `npm install && npm start`.

Once started, the Single Page Application will be available at [http://localhost:4200](http://localhost:4200).

#### Backend

Execute `org.adhuc.cena.menu.MenuGenerationApplication` in your favorite IDE. The application uses [Spring Devtools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html), which means that the application does not need to be restarted after each modification in the project source, but will be re-compiled and reloaded each time.

Once started, the application will be available at [http://localhost:8080](http://localhost:8080).

### Build

From `cena` project folder, execute either `make`, `make build` or `mvn clean package -Pdocker` command. The latter command will be used in all cases to run the build. The default profile will skip the docker image creation.

This build execution uses the [Maven Dockerfile Plugin](https://github.com/spotify/dockerfile-maven) to create a docker image based on a `openjdk:8-alpine` image. More information about this build execution can be found in `cena/pom.xml` and `cena/Dockerfile` files. See [Running with docker-compose](#running-with-docker-compose) to know how to run the built docker image.

This build results in a `cena/target/menu-generation.jar` JAR file and a `menu-generation:latest` docker image. See [Running jar](#running-jar) section to know how to run directly from JAR file, or [Running with docker-compose](#running-with-docker-compose) section to know how to run the dockerized environment.

### Execution

#### Environment Configuration

The project contains a `Makefile` to execute different targets for build and execution. Execution targets enable configuring local environment. Create a `.env` file in the project root folder, then complete the file with the following (with default values) and adapt :

```
PORT=8080
```

#### Running jar

Once the build ends successfully (see [Build](#build) section), the application can be started using `make run` or `java -jar target/menu-generation.jar` command. The latter will be used in all cases to run the application.

Once started, the application will be available at [http://localhost:8080](http://localhost:8080), or the URL corresponding to the port configured in the `.env` file.

#### Running with docker-compose

Once the build ends successfully (see [Build](#build) section), a docker environment can be executed using `make up` or `cd docker && docker-compose up -d` command. The latter command will be used in all cases to run the docker containers. The `docker-compose` configuration file is located at `docker/docker-compose.yml`.

The docker environment can be stopped using `make down` or `cd docker && docker-compose down` command. The latter command will be used in all cases.

## Acceptance tests

From `cena` project folder, execute either `make test` or `mvn verify -Pfunctional-acceptance` command. The latter command will be used in all cases to run the build. The default profile will skip the acceptance tests execution.

The acceptance tests are run against a docker-compose environment. The acceptance tests results are available through `cena/target/site/serenity/index.html` page.

It is too soon to expect having a stable user interface, so the SPA is not tested through acceptance tests.

## Restful API documentation

The API documentation is generated using [Spring-RestDocs](http://projects.spring.io/spring-restdocs/).

Once the application is started, the Rest API documentation will be available at [`http://localhost:8080/api/docs/api-guide.html`](http://localhost:8080/api/docs/api-guide.html).
