1. Download https://www.apache.org/dyn/closer.cgi?path=/kafka/2.6.0/kafka_2.13-2.6.0.tgz
2. Unpack
3. bin\windows\zookeeper-server-start.bat config\zookeeper.properties
4. bin\windows\kafka-server-start.bat config\server.properties

5. bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic streams-plaintext-input
6. bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic streams-wordcount-output --config cleanup.policy=compact

bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe

7. bin\windows\kafka-run-class.bat org.apache.kafka.streams.examples.wordcount.WordCountDemo

8. bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic streams-plaintext-input
9. bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

---------------------
7a. WordCount
7b. WordCountProducer
7c. WordCountConsumer
7d. WordCountForEach