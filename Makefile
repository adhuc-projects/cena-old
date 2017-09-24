maven = ./mvnw

ifeq ($(OS),Windows_NT)
	maven = mvnw.cmd
endif

build: ## Build the application
	$(maven) clean package
