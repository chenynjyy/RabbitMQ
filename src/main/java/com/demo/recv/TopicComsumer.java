package com.demo.recv;

import com.demo.constant.Constant;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class TopicComsumer extends Comsumer {

    /**
     * 接收指定routingkey的*和#
     * @param channel
     * @throws IOException
     */
    @Override
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.TopicExchange, Constant.TopicQueue, Constant.TopicBindingKey);
        super.declareAndBind(channel);
    }

}
