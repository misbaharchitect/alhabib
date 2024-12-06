package org.example.aggregation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.example.Person;
import org.example.serde.PersonDeSerializer;
import org.example.serde.PersonSerializer;

import java.util.Properties;

public class CountKStreamPerson {
    public static void main(String[] args) {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "personcount_app_id");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        KafkaStreams kafkaStreams =
                new KafkaStreams(build(), streamProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                kafkaStreams.close();
            }
        }));
        try {
            kafkaStreams.start();
        } catch (Exception ex) {
            System.out.println("Exception occured");
            ex.printStackTrace();
        }
        System.out.println("started kafka stream with ktable");
    }

    public static Topology build() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        Serde<Person> personSerde = Serdes.serdeFrom(new PersonSerializer(), new PersonDeSerializer());
        KStream<String, Person> inputStream = streamsBuilder.stream("countpersontopic",
                Consumed.with(Serdes.String(), personSerde));

       inputStream.peek((k,v) -> System.out.println("k-" + k + " : v-" + v))
               .print(Printed.<String, Person>toSysOut().withLabel("mylabel"))
               ;
        KGroupedStream<String, Person> myKGroupedStream = inputStream.groupByKey(Grouped.with(Serdes.String(), personSerde));
        KTable<String, Long> count = myKGroupedStream.count(Named.as("count-per-person"));
        count.toStream().print(Printed.<String, Long>toSysOut().withLabel("personcountlabel"));
        return streamsBuilder.build();
    }
}
