package com.demo.send;

import com.demo.constant.Constant;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/15 0015. <br/>
 * 广播模式 <br/>
 * 无routingKey
 */
public class FanoutProducer extends Producer {

    /**
     * routing_key 可为空
     * @param channel
     * @throws IOException
     */
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.FanoutExchange, Constant.FanoutRoutingKey, BuiltinExchangeType.FANOUT, "fanout test !!!");
        super.declareAndBind(channel);
    }

}
