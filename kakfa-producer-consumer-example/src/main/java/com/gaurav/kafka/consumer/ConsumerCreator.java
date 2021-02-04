package com.gaurav.kafka.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.gaurav.kafka.constants.IKafkaConstants;

public class ConsumerCreator {
//https://dzone.com/articles/kafka-producer-and-consumer-example
	public static Consumer<Long, String> createConsumer() {
		final Properties props = new Properties();
//		Above KafkaConsumerExample.createConsumer sets the BOOTSTRAP_SERVERS_CONFIG (“bootstrap.servers”) property to the list of broker addresses we defined earlier. BOOTSTRAP_SERVERS_CONFIG value is a comma separated list of host/port pairs that the Consumer uses to establish an initial connection to the Kafka cluster. Just like the producer, the consumer uses of all servers in the cluster no matter which ones we list here.
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);


//The GROUP_ID_CONFIG identifies the consumer group of this consumer.
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

//		MAX_POLL_RECORDS_CONFIG: The max count of records that the consumer will fetch in one iteration.
//


		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);

//ENABLE_AUTO_COMMIT_CONFIG: When the consumer from a group receives a message it must commit the offset of that record. If this configuration is set to be true then, periodically, offsets will be committed, but, for the production level, this should be false and an offset should be committed manually

		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//		AUTO_OFFSET_RESET_CONFIG: For each consumer group, the last committed offset value is stored. This configuration comes handy if no offset is committed for that group, i.e. it is the new group created.
//
//Setting this value to earliest will cause the consumer to fetch records from the beginning of offset i.e from zero.
//
//Setting this value to latest will cause the consumer to fetch records from the new records. By new records mean those created after the consumer group became active.
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);

//Important notice that you need to subscribe the consumer to the topic consumer.subscribe(Collections.singletonList(TOPIC));. The subscribe method takes a list of topics to subscribe to, and this list will replace the current subscriptions if any.
		final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		return consumer;
	}

}
