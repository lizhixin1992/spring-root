package com.blue.rabbitMQ.test;

import org.springframework.util.SerializationUtils;

import java.io.Serializable;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 15:29 2017/8/30
 */
public class QueueProducer extends BaseMQ{

    public QueueProducer(String endPointName) throws Exception{
        super(endPointName);
    }

    public void sendMessage(Serializable object) throws Exception {
        channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
    }
}
