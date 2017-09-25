PORT=8080
-include .env

DOCKER_COMPOSE_ROOT=docker
DOCKER_COMPOSE_ENV=COMPOSE_PROJECT_NAME=cena PORT=$(PORT)

maven = ./mvnw

ifeq ($(OS),Windows_NT)
	maven = mvnw.cmd
endif

build: ## Build the application as a docker image
	$(maven) clean package -Pdocker

test: ## Run acceptance testing with mvn verify
	$(maven) verify -Pfunctional-acceptance -Dtest.acceptance.dockerEnv.cena.port=$(PORT)

run: ## Run the application
	java -jar target/menu-generation.jar --server.port=$(PORT)

up: ## Start the dockerized local env using docker/docker-compose.yml
	cd $(DOCKER_COMPOSE_ROOT) && $(DOCKER_COMPOSE_ENV) docker-compose pull --ignore-pull-failures && $(DOCKER_COMPOSE_ENV) docker-compose up -d

down: ## Stop the dockerized local env using docker/docker-compose.yml
	cd $(DOCKER_COMPOSE_ROOT) && $(DOCKER_COMPOSE_ENV) docker-compose down

help: ## This help dialog.
	@echo "Usage: make [target]. Find the available targets below:"
	@echo "$$(grep -hE '^\S+:.*##' $(MAKEFILE_LIST) | sed 's/:.*##\s*/:/' | column -c2 -t -s :)"
