package self.ed;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class WordCountProducer {
    private final static String SERVER = "localhost:9092";
    private final static String TOPIC = "streams-plaintext-input";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(CLIENT_ID_CONFIG, "streams-wordcount-producer");
        props.put(BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (long i = 0; i < Long.MAX_VALUE; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "Key" + i, "Value" + (i % 2));
                RecordMetadata metadata = producer.send(record).get();
                producer.flush();
                System.out.println("Produced: " + metadata);
                Thread.sleep(1000);
            }
        }
    }
}
