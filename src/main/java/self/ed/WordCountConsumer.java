package self.ed;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class WordCountConsumer {
    private final static String SERVER = "localhost:9092";
    private final static String TOPIC = "streams-wordcount-output";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(GROUP_ID_CONFIG, "streams-wordcount-consumer");
        props.put(BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        try (KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(singletonList(TOPIC));
            while (true) {
                consumer.poll(ofSeconds(1)).forEach(record ->
                        System.out.println("Consumed: " + record.key() + " -> " + record.value()));
                consumer.commitAsync();
            }
        }
    }
}
