package com.gaurav.kafka.producer;

import static com.gaurav.kafka.producer.ProducerCreator.createProducer;

import com.gaurav.kafka.constants.IKafkaConstants;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SynchronousProducerSender {
//http://cloudurable.com/blog/kafka-tutorial-kafka-producer/index.html
  public static void runProducer(final int sendMessageCount) throws Exception {
    final Producer<Long, String> producer = createProducer();
    long time = System.currentTimeMillis();

    try {
      for (long index = time; index < time + sendMessageCount; index++) {
        final ProducerRecord<Long, String> record =
            new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, index,
                "Hello Mom " + index);
        //Send Records Synchronously
        //The send method returns a Java Future
        RecordMetadata metadata = producer.send(record).get();
//The response RecordMetadata has ‘partition’ where the record was written and the ‘offset’ of the record in that partition.
        long elapsedTime = System.currentTimeMillis() - time;
        System.out.printf("sent record(key=%s value=%s) " +
                "meta(partition=%d, offset=%d) time=%d\n",
            record.key(), record.value(), metadata.partition(),
            metadata.offset(), elapsedTime);

      }
    } finally {
//      Notice the call to flush and close. Kafka will auto flush on its own, but you can also call flush explicitly which will send the accumulated records now. It is polite to close the connection when we are done.
      producer.flush();
      producer.close();
    }
  }


//https://dzone.com/articles/kafka-producer-and-consumer-example
  public static void runProducer() {
    Producer<Long, String> producer = ProducerCreator.createProducer();

    for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
      final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
          "This is record " + index);
      try {
        //Send Records Synchronously
        //The send method returns a Java Future
        RecordMetadata metadata = producer.send(record).get();
//        The response RecordMetadata has ‘partition’ where the record was written and the ‘offset’ of the record in that partition.
        System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
            + " with offset " + metadata.offset());
      } catch (ExecutionException e) {
        System.out.println("Error in sending record");
        System.out.println(e);
      } catch (InterruptedException e) {
        System.out.println("Error in sending record");
        System.out.println(e);
      }
    }
  }

}
