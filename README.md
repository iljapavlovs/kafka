## KAFKA start

https://kafka.apache.org/quickstart 

## Resources

* Some properties described - https://dzone.com/articles/kafka-setup
* Neat example of Producer and Consumer in Java - 
  
    * https://dzone.com/articles/kafka-producer-and-consumer-example
    * http://cloudurable.com/blog/kafka-tutorial-kafka-producer/index.html

* [Testing Kafka based applications](https://medium.com/test-kafka-based-applications/https-medium-com-testing-kafka-based-applications-85d8951cec43) 
  * E2E setup in docker for testing - https://github.com/sysco-middleware/kafka-testing/tree/master/e2e

* [Creating Topics on startup in docker](https://github.com/confluentinc/examples/blob/f854ac008952346ceaba0d1f1a352788f0572c74/microservices-orders/docker-compose.yml#L182-L215) -     




### Logging set up for Kafka

* http://cloudurable.com/blog/kafka-tutorial-kafka-consumer/index.html
  
If you don’t set up logging well, it might be hard to see the consumer get the messages.

Kafka like most Java libs these days uses sl4j. You can use Kafka with Log4j, Logback or JDK logging. We used logback in our gradle build (compile 'ch.qos.logback:logback-classic:1.2.2').
```
~/kafka-training/lab4/solution/src/main/resources/logback.xml
```
```
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>


    <logger name="org.apache.kafka" level="INFO"/>
    <logger name="org.apache.kafka.common.metrics" level="INFO"/>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```
Notice that we set org.apache.kafka to INFO, otherwise we will get a lot of log messages. You should run it set to debug and read through the log messages. It gives you a flavor of what Kafka is doing under the covers. Leave org.apache.kafka.common.metrics or what Kafka is doing under the covers is drowned by metrics logging.


