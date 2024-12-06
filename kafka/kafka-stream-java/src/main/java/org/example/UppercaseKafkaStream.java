package org.example;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class UppercaseKafkaStream {
    private static StreamsBuilder builder;

    public static void main(String[] args) throws InterruptedException {
        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> simpleFirstStream = builder.stream("myfirsttopic",
                Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> upperCasedStream =
                simpleFirstStream.mapValues(value -> {
                    System.out.println("received: " + value);
                    return value.toUpperCase();
                });
        // The KStream.to method creates a processing node that writes
        // the final transformed records to a Kafka topic.
        // It is a child of the upperCasedStream, so it receives all of its inputs
        // directly from the results of the mapValues operation.
        upperCasedStream.to("myfirstouttopic", Produced.with(stringSerde, stringSerde));

        Properties streamProperties = new Properties();
        // The StreamsConfig.APPLICATION_ID_CONFIG property uniquely identifies
        // your Kafka Streams application. Kafka Streams instances with
        // the same application.id are considered one logical application.
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "myfirst_kafkastream_app_id");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        Topology topology = builder.build(streamProperties);

//        try(KafkaStreams kafkaStreams =
//                    new KafkaStreams(topology, streamProperties)) {
            KafkaStreams kafkaStreams =
                    new KafkaStreams(topology, streamProperties);

            System.out.println("Hello World Yelling App Started");
            try {
                kafkaStreams.start();
            } catch (Exception ex) {
                System.out.println("Exception occured");
                ex.printStackTrace();
            }
//            TimeUnit.SECONDS.sleep(10);
            System.out.println("Shutting down the Yelling APP now");
//        }
    }
}
