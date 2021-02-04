package com.gaurav.kafka.producer;

import static com.gaurav.kafka.producer.ProducerCreator.createProducer;

import com.gaurav.kafka.constants.IKafkaConstants;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class AsynchronousProducerSender {

//  http://cloudurable.com/blog/kafka-tutorial-kafka-producer/index.html
  public static void runProducer(final int sendMessageCount) throws InterruptedException {
    final Producer<Long, String> producer = createProducer();
    long time = System.currentTimeMillis();
    final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

    try {
      for (long index = time; index < time + sendMessageCount; index++) {
        final ProducerRecord<Long, String> record =
            new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, index, "Hello Mom " + index);
//The big difference here will be that we use a lambda expression to define a callback.
//        Notice the use of a CountDownLatch so we can send all N messages and then wait for them all to send.

        /**
         * Async Interface Callback and Async Send Method
         * Kafka defines a Callback interface that you use for asynchronous operations. The callback interface allows code to execute when the request is complete. The callback executes in a background I/O thread so it should be fast (don’t block it). The onCompletion(RecordMetadata metadata, Exception exception) gets called when the asynchronous operation completes. The metadata gets set (not null) if the operation was a success, and the exception gets set (not null) if the operation had an error.
         *
         * The async send method is used to send a record to a topic, and the provided callback gets called when the send is acknowledged. The send method is asynchronous, and when called returns immediately once the record gets stored in the buffer of records waiting to post to the Kafka broker. The send method allows sending many records in parallel without blocking to wait for the response after each one.
         *
         * Since the send call is asynchronous it returns a Future for the RecordMetadata that will be assigned to this record. Invoking get() on this future will block until the associated request completes and then return the metadata for the record or throw any exception that occurred while sending the record. KafkaProducer
         */
        producer.send(record, (metadata, exception) -> {
          long elapsedTime = System.currentTimeMillis() - time;
          if (metadata != null) {
            System.out.printf("sent record(key=%s value=%s) " +
                    "meta(partition=%d, offset=%d) time=%d\n",
                record.key(), record.value(), metadata.partition(),
                metadata.offset(), elapsedTime);
          } else {
            exception.printStackTrace();
          }
          countDownLatch.countDown();
        });
      }
      countDownLatch.await(25, TimeUnit.SECONDS);
    } finally {
      producer.flush();
      producer.close();
    }
  }

}
