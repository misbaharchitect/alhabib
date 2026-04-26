# Kafka installation
docker run -d -p 9092:9092 --name broker apache/kafka:3.9.2

# Login to the docker container
docker exec -it -w /opt/kafka/bin broker bash

# console-producer with key:
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic myfirsttopic

# console-consumer with consumer-group:
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myfirsttopic