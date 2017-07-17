package com.demo.send;

import com.demo.constant.Constant;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/15 0015.<br/>
 * 主题模式 <br/>
 * routingKey与bindingKey做模式匹配
 */
public class TopicProducer extends Producer {

    /**
     * 发送消息，不需要指定*或#的routingkey
     * @param channel
     * @throws IOException
     */
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.TopicExchange, Constant.TopicRoutingKey, BuiltinExchangeType.TOPIC, "topic test !!!");
        super.declareAndBind(channel);
    }
}
