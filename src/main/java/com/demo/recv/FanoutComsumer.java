package com.demo.recv;

import com.demo.constant.Constant;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class FanoutComsumer extends Comsumer {

    @Override
    protected void declareAndBind(Channel channel) throws IOException {
        init(Constant.FanoutExchange, Constant.FanoutQueue, Constant.FanoutBindingKey);
        super.declareAndBind(channel);
    }

}
