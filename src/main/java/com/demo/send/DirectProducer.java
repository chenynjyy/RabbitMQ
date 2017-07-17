package com.demo.send;

import com.demo.constant.Constant;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/15 0015. <br/>
 * 路由/直连模式 routing <br/>
 * routingKey与bindingKey一致
 */
public class DirectProducer extends Producer {

    /**
     * binding_key与routing_key一致
     * @param channel
     * @throws IOException
     */
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.DirectExchange, Constant.DirectRoutingKey, BuiltinExchangeType.DIRECT, "direct test !!!");
        super.declareAndBind(channel);
    }


}
