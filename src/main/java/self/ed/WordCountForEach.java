package self.ed;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.*;

public class WordCountForEach {
    private final static String SERVER = "localhost:9092";
    private final static String TOPIC = "streams-wordcount-output";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(APPLICATION_ID_CONFIG, "streams-wordcount-for-each");
        props.put(BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(TOPIC).foreach((key, value) -> System.out.println("Consumed: " + key + " -> " + value));

        Topology topology = builder.build();
        System.out.println(topology.describe());

        try (KafkaStreams streams = new KafkaStreams(topology, props)) {
            streams.start();
            while (true) {
                // no-op
            }
        }
    }
}
