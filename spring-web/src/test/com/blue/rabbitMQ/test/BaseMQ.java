package com.blue.rabbitMQ.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 15:24 2017/8/30
 */
public abstract class BaseMQ {

    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public BaseMQ(String endpointName) throws Exception {
        this.endPointName = endpointName;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //hostname of your rabbitmq server
        factory.setHost("127.0.0.1");
        factory.setUsername("lzx");
        factory.setPassword("lzx");

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(endpointName, false, false, false, null);
    }


    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws Exception
     */
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }

}
