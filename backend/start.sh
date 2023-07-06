./mvnw clean install -Dmaven.test.skip=true
docker-compose build
docker-compose up