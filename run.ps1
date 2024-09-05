mvn clean package -DskipTests

# build FE here...

docker build -t gymkeeper-app .

docker-compose up