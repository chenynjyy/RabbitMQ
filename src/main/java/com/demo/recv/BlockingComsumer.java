package com.demo.recv;

import com.demo.Client;
import com.demo.constant.Constant;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class BlockingComsumer extends Client {

    String exchange;
    String queue;
    String bindingKey;

    protected void init(String exchange, String queue, String bindingKey) {
        this.exchange = (this.exchange == null || this.exchange.length() == 0) ? exchange : this.exchange;
        this.queue = (this.queue == null || this.queue.length() == 0) ? queue : this.queue;
        this.bindingKey = (this.bindingKey == null || this.bindingKey.length() == 0) ? bindingKey : this.bindingKey;
    }

    public BlockingComsumer setExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public BlockingComsumer setQueue(String queue) {
        this.queue = queue;
        return this;
    }

    public BlockingComsumer setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
        return this;
    }

    @Override
    protected void declareAndBind(Channel channel) throws IOException {
        this.exchange = (this.exchange == null || this.exchange.length() == 0) ? Constant.TopicExchange : this.exchange;
        this.queue = (this.queue == null || this.queue.length() == 0) ? Constant.TopicQueue : this.queue;
        this.bindingKey = (this.bindingKey == null || this.bindingKey.length() == 0) ? Constant.TopicBindingKey : this.bindingKey;
        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchange, bindingKey);
    }

    @Override
    protected void action(Channel channel) throws IOException {
        while(true) {
            GetResponse response = channel.basicGet(queue, true);
            if(response == null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            } else {
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
            }

        }
    }

    @Override
    protected void ack(Channel channel) {
        //nothing to do
    }

}
