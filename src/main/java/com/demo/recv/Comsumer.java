package com.demo.recv;

import com.demo.Client;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public abstract class Comsumer extends Client {

    String exchange;
    String queue;
    String bindingKey;
    List<Long> deliverTags = new ArrayList<>();
    boolean isAck = true;

    protected void init(String exchange, String queue, String bindingKey) {
        this.exchange = (this.exchange == null || this.exchange.length() == 0) ? exchange : this.exchange;
        this.queue = (this.queue == null || this.queue.length() == 0) ? queue : this.queue;
        this.bindingKey = (this.bindingKey == null || this.bindingKey.length() == 0) ? bindingKey : this.bindingKey;
    }

    public Comsumer setExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public Comsumer setQueue(String queue) {
        this.queue = queue;
        return this;
    }

    public Comsumer setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
        return this;
    }

    public Comsumer setIsAck(boolean isAck) {
        this.isAck = isAck;
        return this;
    }

    protected void declareAndBind(Channel channel) throws IOException {
        /**
         * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
         * queue:队列名称
         * durable:终止server进程，重启后队列是否还存在
         * exclusive：当connection销毁后，队列是否还存在
         * autodelete：队列无客户端使用时，自动清除，如channel回收后自动清除
         * arguments：没了解
         */
        AMQP.Queue.DeclareOk queueOk = channel.queueDeclare(queue, true, false, false, null);
        /**
         * bindingKey：三点
         *      1、direct：可以为空
         *
         */
        AMQP.Queue.BindOk bindOk = channel.queueBind(queue, exchange, bindingKey);

        logger.info(
                "queue declare = " + queueOk.toString() + "\n" +
                "bind declare = " + bindOk.toString() + "\n" +
                "------------------------------------------");
    }

    protected void action(Channel channel) throws IOException {
        while(true) {
            GetResponse response = channel.basicGet(queue, false);
            if(response == null)
                return ;
            int messageCount = response.getMessageCount();
            BasicProperties basicProperties = response.getProps();
            Envelope envelope = response.getEnvelope();
            byte[] body = response.getBody();

            logger.info(
                    "messageCount = " + messageCount + "\n" +
                            "basicProperties = " + basicProperties.toString() + "\n" +
                            "envelope exchange = " + envelope.getExchange() + ", envelope routingkey = " + envelope.getRoutingKey() + ", envelope deliverytag = " + envelope.getDeliveryTag() + "\n" +
                            "body = " + new String(body) + "\n" +
                            "---------------------------");

            this.deliverTags.add(envelope.getDeliveryTag());
        }

    }

    @Override
    protected void ack(Channel channel) {
        if(isAck) {
            for(Long deliverTag : deliverTags) {
                boolean ack = true;
                try {
                    channel.basicAck(deliverTag, false);
                } catch (IOException e) {
                    ack = false;
                }
                logger.info("deliverytag " + deliverTag + " is ack :" + ack + "\n" +
                        "---------------------------");
            }
        }
    }
}
