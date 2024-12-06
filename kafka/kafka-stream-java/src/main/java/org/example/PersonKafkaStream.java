package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.example.serde.PersonDeSerializer;
import org.example.serde.PersonSerializer;

import java.util.Properties;

public class PersonKafkaStream {

    public static void main(String[] args) throws InterruptedException {
        StreamsBuilder builder = new StreamsBuilder();
        Serde<Person> personSerde = Serdes.serdeFrom(new PersonSerializer(), new PersonDeSerializer());
        Serde<String> stringSerde = Serdes.String();
        KStream<String, Person> simpleFirstStream = builder.stream("personintopic",
                Consumed.with(stringSerde, personSerde));

        KStream<String, String> map = simpleFirstStream.mapValues((k, v) -> {
            System.out.println("key: " + k);
            System.out.println("value: " + v);
            return v.getFirstName().toUpperCase();
        });

//        KStream<String, String> map = simpleFirstStream.mapValues((k, v) -> {
//            System.out.println("value: " + v);
//            return v.toUpperCase();
//        });

        map.to("personouttopic", Produced.with(stringSerde, stringSerde));

        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "person_kafkastream_app_id");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        Topology topology = builder.build(streamProperties);
        Topology topology = builder.build();


        KafkaStreams kafkaStreams =
                new KafkaStreams(topology, streamProperties);
//        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        System.out.println("Person App Started");
        try {
            kafkaStreams.start();
        } catch (Exception ex) {
            System.out.println("Exception occured");
            ex.printStackTrace();
        }
//        TimeUnit.SECONDS.sleep(10);
    }
}
