package self.ed;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;

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
        KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(props);
        try (consumer) {
            consumer.subscribe(singletonList(TOPIC));
            while (true) {
                ConsumerRecords<String, Long> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                System.out.println("Consumed: " + consumerRecords.count());
                consumerRecords.forEach(record -> System.out.println("Consumed: " + record));
                consumer.commitAsync();
            }
        }
    }
}
