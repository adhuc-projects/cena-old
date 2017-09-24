PORT=8080
-include .env

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
