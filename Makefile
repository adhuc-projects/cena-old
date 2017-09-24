PORT=8080
-include .env

DOCKER_COMPOSE_ROOT=docker
DOCKER_COMPOSE_ENV=COMPOSE_PROJECT_NAME=cena PORT=$(PORT)

maven = ./mvnw
build_cmd = $(maven) clean package -Pdocker

ifeq ($(OS),Windows_NT)
	maven = ./mvnw.cmd
	build_cmd = $(maven) clean package && docker build -t menu-generation:latest target
endif

build: ## Build the application as a docker image
	$(build_cmd)

run: ## Run the application
	java -jar target/menu-generation.jar --server.port=$(PORT)

up: ## Start the dockerized local env using docker/docker-compose.yml
	cd $(DOCKER_COMPOSE_ROOT) && $(DOCKER_COMPOSE_ENV) docker-compose pull --ignore-pull-failures && $(DOCKER_COMPOSE_ENV) docker-compose up -d

down: ## Stop the dockerized local env using docker/docker-compose.yml
	cd $(DOCKER_COMPOSE_ROOT) && $(DOCKER_COMPOSE_ENV) docker-compose down
