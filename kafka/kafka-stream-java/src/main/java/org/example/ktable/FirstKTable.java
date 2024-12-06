package org.example.ktable;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Properties;

public class FirstKTable {

    public static void main(String[] args) {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "first_ktable_app_id");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        KafkaStreams kafkaStreams =
                new KafkaStreams(build(), streamProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
        System.out.println("started kafka stream with ktable");
    }

    public static Topology build() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KTable<String, String> wordsTable = streamsBuilder.table("ktableintopic",
                Consumed.with(Serdes.String(), Serdes.String()),
                Materialized.as("words-store"));

        wordsTable.filter((k,v) -> {
//                    System.out.println("v: " + v);
            return v.length() > 2;
                })
                .mapValues(v -> v.toUpperCase())
                .toStream()
                .peek((k, v) -> System.out.println(k + ":" + v))
                .print(Printed.<String, String>toSysOut().withLabel("words-ktable"));

        return streamsBuilder.build();
    }
}
