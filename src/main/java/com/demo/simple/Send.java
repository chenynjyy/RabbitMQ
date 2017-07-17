package com.demo.simple;

import com.demo.build.RMQFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class Send {

    private final static String QUEUE_NAME = "hehe";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = RMQFactory.build();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
