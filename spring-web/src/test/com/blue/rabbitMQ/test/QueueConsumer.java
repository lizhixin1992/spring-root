package com.blue.rabbitMQ.test;

import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 16:01 2017/8/30
 */
public class QueueConsumer extends BaseMQ implements Runnable{
    public QueueConsumer(String endPointName) throws Exception{
        super(endPointName);
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    Map map = (HashMap) SerializationUtils.deserialize(body);
                    System.out.println("Message Number "+ map.get("message number") + " received.");
                }
            };
            channel.basicConsume(endPointName, true,consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

