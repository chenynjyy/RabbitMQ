package com.demo.constant;

import com.rabbitmq.client.AMQP;

/**
 * Created by chenynjyy on 2017/7/15 0015.
 */
public class Constant {

    public static final String DirectExchange = "test_direct_exchange";
    public static final String TopicExchange = "test_topic_exchange";

    public static final String RoutingKey = "test_routing_key";
    public static final boolean Mandatory = true;
    public static final boolean Immediate = false;
    public static final AMQP.BasicProperties PROPERTIES = new AMQP.BasicProperties().builder().build();

    public static final String Queue = "test_queue";

}
