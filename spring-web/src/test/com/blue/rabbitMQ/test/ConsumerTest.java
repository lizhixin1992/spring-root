package com.blue.rabbitMQ.test;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 16:03 2017/8/30
 */
public class ConsumerTest {

    public ConsumerTest() throws Exception{

        QueueConsumer consumer = new QueueConsumer("lzxQueue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        new ConsumerTest();
    }
}
