package org.example.aggregation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class CountKStream {
    public static void main(String[] args) {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "count_app_id");
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
        KStream<String, String> inputStream = streamsBuilder.stream("countintopic",
                Consumed.with(Serdes.String(), Serdes.String()));

       inputStream.peek((k,v) -> System.out.println("k-" + k + " : v-" + v))
               .print(Printed.<String, String>toSysOut().withLabel("mylabel"))
               ;
        KGroupedStream<String, String> myKGroupedStream = inputStream.groupByKey(Grouped.with(Serdes.String(), Serdes.String()));
        KTable<String, Long> count = myKGroupedStream.count(Named.as("count-per-alphabet"));
        count.toStream().print(Printed.<String, Long>toSysOut().withLabel("countlabel"));
        return streamsBuilder.build();
    }
}
