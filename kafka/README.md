docker exec -it -w /opt/kafka/bin <container-id> bash


console-producer with key:

./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic myfirsttopic --property parse.key=true --property key.separator=:

console-consumer with consumer-group:

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myfirsttopic --consumer-property group.id=a --property print.key=true --property key.separator=:


Commom for all:
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

Docker:
./kafka-topics.sh --bootstrap-server localhost:9092 --list

./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic 
mynewtopic

./kafka-topics.sh --bootstrap-server localhost:9092 --describe

For Docker people:
docker run -p 9092:9092 --name broker apache/kafka:3.9.0
docker ps
docker exec -it -w /opt/kafka/bin <container-id> bash

For Non-Docker people:

$ tar -xzf kafka_2.13-3.9.0.tgz
$ cd kafka_2.13-3.9.0
$ KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
$ bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/kraft/reconfig-server.properties
$ bin/kafka-server-start.sh config/kraft/reconfig-server.properties



Zoom Meeting  Id: 
Meeting ID: 863 6108 8102

Passcode: 895248

https://us06web.zoom.us/j/86361088102?pwd=ayhm6rEBtzhL1eqJufthgGuzWWVgJI.1


LET ME KNOW WHEN PEOPLE ARE BACK




Lunch Break:
12:50 pm to 2:30 pm for Friday
1 pm to 2 pm for Saturday
