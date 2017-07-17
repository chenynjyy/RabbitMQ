package com.demo.recv;

import com.demo.constant.Constant;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/15 0015.
 */
public class DirectComsumer extends Comsumer {

    @Override
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.DirectExchange, Constant.DirectQueue, Constant.DirectBindingKey);
        super.declareAndBind(channel);
    }
}
