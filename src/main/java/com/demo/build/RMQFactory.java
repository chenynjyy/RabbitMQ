package com.demo.build;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenynjyy on 2017/7/11.
 */
public class RMQFactory {

    /**
     * 创建连接工厂
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static ConnectionFactory build() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.4.157");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

}
