package com.demo;

import com.demo.build.RMQFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenynjyy on 2017/7/15 0015.
 */
public abstract class Client {

    protected abstract void declareAndBind(Channel channel) throws IOException;

    protected abstract void action(Channel channel) throws IOException;

    protected void go() throws IOException, TimeoutException {

        ConnectionFactory factory = RMQFactory.build();

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //建立通道
            channel = connection.createChannel();

            /**
             * 设置为确认模式
             * 发送消息后，需要获取到确认消息是否消息正常到达broker，如果直接close-channel的话，会自动确认
             */
            channel.confirmSelect();

            //声明交换机、队列，绑定路由规则
            declareAndBind(channel);

            //发送/接收数据
            action(channel);

            /**
             * 确认消息是否到达broker
             */
            boolean isConfirm = channel.waitForConfirms();
            if(isConfirm) {
                System.out.println("发送broker成功");
            } else {
                System.out.println("发送broker失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(channel != null)
                try {
                    channel.close();
                } catch (Exception e) {
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (IOException e) {
                }
        }


    }

}
