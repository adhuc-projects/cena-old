maven = ./mvnw
build_cmd = $(maven) clean package -Pdocker

ifeq ($(OS),Windows_NT)
	maven = ./mvnw.cmd
	build_cmd = $(maven) clean package && docker build -t menu-generation:latest target
endif

build: ## Build the application
	$(build_cmd)
