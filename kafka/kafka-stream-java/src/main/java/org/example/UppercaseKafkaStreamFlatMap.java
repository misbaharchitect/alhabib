package org.example;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class UppercaseKafkaStreamFlatMap {
    public static void main(String[] args) throws InterruptedException {
        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> simpleFirstStream = builder.stream("myfirsttopic",
                Consumed.with(Serdes.String(), Serdes.String()));
        /*KStream<String, String> stringStringKStream = simpleFirstStream.flatMapValues(v -> {
            System.out.println("received: " + v);
            String[] allWords = v.split(" ");
            return Arrays.stream(allWords).toList();
        });*/
        KStream<String, String> stringStringKStream = simpleFirstStream.flatMapValues(v -> {
            System.out.println("received: " + v);
            String[] allWords = v.split(" ");
            return Arrays.stream(allWords).toList();
        });

        stringStringKStream.to("myfirstouttopic", Produced.with(stringSerde, stringSerde));
//        stringStringKStream.to("myfirstouttopic");

        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "myfirst_kafkastream_app_id");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        Topology topology = builder.build(streamProperties);

            KafkaStreams kafkaStreams =
                    new KafkaStreams(topology, streamProperties);

            System.out.println("Hello World Yelling App Started");
            kafkaStreams.start();
//            TimeUnit.SECONDS.sleep(10);
            System.out.println("Shutting down the Yelling APP now");
    }
}
