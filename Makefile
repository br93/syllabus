APP = app
APP_FOLDER = ../../app
SERVICES_FOLDER = ./syllabus-services

JAR = target/*.jar
MV_JAR = mv $(JAR) ${APP_FOLDER}/${APP}
MVND_PACKAGE = mvnd clean package
TARGET_FOLDER = ./target

ENV_GO = GOOS=linux CGO_ENABLED=0
BUILD_GO = go build -o $(APP_FOLDER)/$(APP)

DOCKER_COMPOSE = docker-compose -f ./syllabus-docker
DATABASE_COMPOSE = $(DOCKER_COMPOSE)/docker-db-compose.yml
KAFKA_COMPOSE = $(DOCKER_COMPOSE)/docker-kafka-compose.yml
SERVICE_COMPOSE = $(DOCKER_COMPOSE)/docker-services-compose.yml

up: build_network up_database up_kafka up_services

up_database:
	@echo "Starting database compose..."
	$(DATABASE_COMPOSE) up -d
	@echo "Done"

up_kafka:
	@echo "Starting kafka compose..."
	$(KAFKA_COMPOSE) up -d
	@echo "Done"

up_services:
	@echo "Starting service compose..."
	$(SERVICE_COMPOSE) up -d
	@echo "Done"

up_build: down build_network up_database up_kafka build_go pack_java 
	@echo "Building and starting service compose..."
	$(SERVICE_COMPOSE) up --build -d
	@echo "Cleaning untag images..."
	docker rmi `docker images | grep "<none>" | awk {'print $3'}`
	@echo "Done"

down:
	@echo "Stopping compose..."
	$(DATABASE_COMPOSE) down
	$(KAFKA_COMPOSE) down
	$(SERVICE_COMPOSE) down
	@echo "Done"

build_network:
	@echo "Creating network..."
	docker network create -d bridge syllabus-network || true
	@echo "Done" 

images_clean:
	@echo "Cleaning untag images..."
	docker rmi `docker images | grep "<none>" | awk {'print $3'}`
	@echo "Done"

build_go: build_auth build_settings

pack_java: pack_discovery_service pack_api_gateway pack_sidecar_auth pack_sidecar_settings pack_acc_management pack_settings_batch pack_students pack_core pack_recommendations

build_auth:
	@echo "Building authentication service..."
	cd $(SERVICES_FOLDER)/syllabus-auth-go && cp .env-docker .env && env $(ENV_GO) $(BUILD_GO)-auth .
	@echo "Done"

build_settings:
	@echo "Building settings service..."
	cd $(SERVICES_FOLDER)/syllabus-settings-go && env $(ENV_GO) $(BUILD_GO)-settings .
	@echo "Done" 

pack_discovery_service:
	@echo "Packaging discovery service..."
	cd $(SERVICES_FOLDER)/syllabus-discovery-service && $(MVND_PACKAGE) && ${MV_JAR}-discovery-service.jar
	@echo "Done"

pack_api_gateway:
	@echo "Packaging api gateway..."
	cd ${SERVICES_FOLDER}/syllabus-api-gateway && ${MVND_PACKAGE} && ${MV_JAR}-api-gateway.jar
	@echo "Done"

pack_sidecar_settings:
	@echo "Packaging settings sidecar service..."
	cd ${SERVICES_FOLDER}/syllabus-settings && ${MVND_PACKAGE} && ${MV_JAR}-settings-sidecar.jar
	@echo "Done"

pack_sidecar_auth:
	@echo "Packaging auth sidecar service..."
	cd ${SERVICES_FOLDER}/syllabus-auth && ${MVND_PACKAGE} && ${MV_JAR}-auth-sidecar.jar
	@echo "Done"
pack_acc_management:
	@echo "Packaging account management service..."
	cd ${SERVICES_FOLDER}/syllabus-account-management && ${MVND_PACKAGE} && ${MV_JAR}-account-management.jar
	@echo "Done"

pack_settings_batch:
	@echo "Packaging settings batch service..."
	cd ${SERVICES_FOLDER}/syllabus-settings-batch && ${MVND_PACKAGE} && ${MV_JAR}-settings-batch.jar
	@echo "Done"

pack_students:
	@echo "Packaging students service..."
	cd ${SERVICES_FOLDER}/syllabus-students && ${MVND_PACKAGE} && ${MV_JAR}-students.jar
	@echo "Done"

pack_core:
	@echo "Packaging core service..."
	cd ${SERVICES_FOLDER}/syllabus-core && ${MVND_PACKAGE} && ${MV_JAR}-core.jar
	@echo "Done"

pack_recommendations:
	@echo "Packaging recommendations service..."
	cd ${SERVICES_FOLDER}/syllabus-recommendations && ${MVND_PACKAGE} && ${MV_JAR}-recommendations.jar
	@echo "Done"
