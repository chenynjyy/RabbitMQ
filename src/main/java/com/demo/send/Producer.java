package com.demo.send;

import com.demo.Client;
import com.demo.constant.Constant;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/17.
 */
public abstract class Producer extends Client {

    String exchange;
    String routingKey;
    BuiltinExchangeType type;
    String body;

    protected void init(String exchange, String routingKey, BuiltinExchangeType type, String body) {
        this.exchange = exchange;
        this.routingKey = (this.routingKey == null || this.routingKey.length() == 0) ? routingKey : this.routingKey;
        this.type = type;
        this.body = (this.body == null || this.body.length() == 0) ? body : this.body;
    }

    public Producer setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public Producer setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * 1、定义交换机
     * 2、定义队列
     * 3、定义交换机与队列之间的绑定
     *
     * params：
     * 1、String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments
     *   exchange:交换机名称
     *   type:交换机类型
     *   durable：交换机是否保存，重启之后是否还在
     *   autoDelete：交换机使用完是否销毁
     *   arguments:没研究
     * 2、String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
     *   queue：队列名称
     *   durable：队列server重启后是否还存在
     *   exclusive：是否connection销毁后就一并销毁
     *   autoDelete：是否没用之后，队列里边没数据之后就销毁
     *   arguments：没了解
     * 3、String destination, String source, String routingKey, Map<String, Object> arguments
     *   destination：目标地址，通常指queue
     *   source：源地址，通常指exchange
     *   routingKey：路由名称，给这条连线命名为什么
     *   arguments：没了解
     */
    protected void declareAndBind(Channel channel) throws IOException {
        AMQP.Exchange.DeclareOk exchangeOk = channel.exchangeDeclare(exchange, type, true, Constant.Immediate, null);

        logger.info("exchangeOk:" + exchangeOk.toString() + "\n" +
                "------------------------------------------");
    }

    /**
     * 发布消息（发布到路由，如果没有队列，会根据Mandatory标志位做不同动作）
     * String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body
     *   exchange：交换机名称
     *   routingKey：设置的bindkey，也就是路由key
     *   mandatory：通过routingkey不能找到queue后所做的动作，true，返还basic.return给生产者，false，直接在broker丢弃
     *   immediate：routingkey对应的queue没有一个connection，true，返还basic.return给broker，false，直接在broker丢弃
     *   props：其他配置项，可用MessageProperties
     *      content-type:可参考http头协议
     *      delivery-mode：1代表不持久化，2代表持久化，就是宕机后会从数据文件中重新获取，但从内存到磁盘保存会有几秒钟的同步延迟
     *      其他如cluster-id等可酌情添加
     *   body：传输的实际数据
     */
    protected void action(Channel channel) throws IOException {
        /**
         * 发送到指定的routingKey
         */
        channel.basicPublish(exchange, routingKey, true, false, MessageProperties.BASIC, body.getBytes());
    }

    protected void ack(Channel channel) {
        /**
         * 确认消息是否到达broker
         */
        boolean isConfirm = true;
        try {
            isConfirm = channel.waitForConfirms();
        } catch (InterruptedException e) {
            isConfirm = false;
        }
        if(isConfirm) {
            logger.info("发送broker成功");
        } else {
            logger.warn("发送broker失败");
        }
    }

}
