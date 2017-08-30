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
        boolean f = consumer.receive();
        System.out.println(f);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        new ConsumerTest();
    }
}
