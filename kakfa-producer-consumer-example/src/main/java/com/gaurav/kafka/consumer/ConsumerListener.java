package com.gaurav.kafka.consumer;

import static com.gaurav.kafka.consumer.ConsumerCreator.createConsumer;

import com.gaurav.kafka.constants.IKafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class ConsumerListener {

//  http://cloudurable.com/blog/kafka-tutorial-kafka-consumer/index.html

  /**
   * Notice you use ConsumerRecords which is a group of records from a Kafka topic partition. The ConsumerRecords class is a container that holds a list of ConsumerRecord(s) per partition for a particular topic. There is one ConsumerRecord list for every topic partition returned by a the consumer.poll().
   *
   * Notice if you receive records (consumerRecords.count()!=0), then runConsumer method calls consumer.commitAsync() which commit offsets returned on the last call to consumer.poll(…) for all the subscribed list of topic partitions.
   *
   * Kafka Consumer Poll method
   * The poll method returns fetched records based on current partition offset. The poll method is a blocking method waiting for specified time in seconds. If no records are available after the time period specified, the poll method returns an empty ConsumerRecords.
   *
   * When new records become available, the poll method returns straight away.
   *
   * You can can control the maximum records returned by the poll() with props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);. The poll method is not thread safe and is not meant to get called from multiple threads.
   * @throws InterruptedException
   */
  public static void runConsumer() throws InterruptedException {
    final Consumer<Long, String> consumer = createConsumer();

    final int giveUp = 100;
    int noRecordsCount = 0;

    while (true) {
      final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);

      if (consumerRecords.count() == 0) {
        noRecordsCount++;
        if (noRecordsCount > giveUp) {
          break;
        } else {
          continue;
        }
      }

      consumerRecords.forEach(record -> {
        System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
            record.key(), record.value(),
            record.partition(), record.offset());
      });

      consumer.commitAsync();
    }
    consumer.close();
    System.out.println("DONE");
  }

//https://dzone.com/articles/kafka-producer-and-consumer-example
  public static void runConsumer2() {
    Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

    int noMessageToFetch = 0;

    while (true) {
      final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
      if (consumerRecords.count() == 0) {
        noMessageToFetch++;
        if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
          break;
        else
          continue;
      }

      consumerRecords.forEach(record -> {
        System.out.println("Record Key " + record.key());
        System.out.println("Record value " + record.value());
        System.out.println("Record partition " + record.partition());
        System.out.println("Record offset " + record.offset());
      });
      consumer.commitAsync();
    }
    consumer.close();
  }
}
