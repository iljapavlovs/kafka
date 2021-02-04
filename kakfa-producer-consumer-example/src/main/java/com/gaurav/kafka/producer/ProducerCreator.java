package com.gaurav.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.gaurav.kafka.constants.IKafkaConstants;

public class ProducerCreator {

	public static Producer<Long, String> createProducer() {
		Properties props = new Properties();
//		“bootstrap.servers" property - Kafka Brokers list
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
//		The CLIENT_ID_CONFIG (“client.id”) is an id to pass to the server when making requests so the server can track the source of requests beyond just IP/port by passing a producer name for things like server-side request logging.
		props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
//		The KEY_SERIALIZER_CLASS_CONFIG (“key.serializer”) is a Kafka Serializer class for Kafka record keys that implements the Kafka Serializer interface. Notice that we set this to LongSerializer as the message ids in our example are longs.
				props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
//		The VALUE_SERIALIZER_CLASS_CONFIG (“value.serializer”) is a Kafka Serializer class for Kafka record values that implements the Kafka Serializer interface. Notice that we set this to StringSerializer as the message body in our example are strings.
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}
}