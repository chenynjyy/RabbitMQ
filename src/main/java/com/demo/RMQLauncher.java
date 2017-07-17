package com.demo;

import com.demo.recv.DirectComsumer;
import com.demo.recv.FanoutComsumer;
import com.demo.send.DirectProducer;
import com.demo.send.FanoutProducer;
import com.demo.send.TopicProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenynjyy on 2017/7/15 0015.
 */
public class RMQLauncher {

    private static final Logger logger = LoggerFactory.getLogger(RMQLauncher.class);

    public static void main(String[] args) throws IOException, TimeoutException {
//        direct();
//
//        fanout();

        topic();
    }

    private static void direct() throws IOException, TimeoutException {
        logger.info("======================================");
        logger.info("direct test ");

        new DirectProducer().go();

        new DirectComsumer().go();
    }

    private static void fanout() throws IOException, TimeoutException {
        logger.info("======================================");
        logger.info("fanout test ");

        new FanoutProducer().setRoutingKey("kk").go();

//        new FanoutComsumer().setBindingKey("").setIsAck(false).go();
        new FanoutComsumer().setBindingKey("kk").setQueue("pp").go();
    }

    private static void topic() throws IOException, TimeoutException {
        logger.info("======================================");
        logger.info("topic test ");

        new TopicProducer().setRoutingKey("qq.haha").setBody("topic is chen").go();
        new TopicProducer().setRoutingKey("qq.tata").setBody("topic is yu").go();
        new TopicProducer().setRoutingKey("qq.papa.jj").setBody("topic is nan").go();

//        new TopicComsumer().setQueue(UUID.randomUUID().toString()).setBindingKey("qq.*").go();
    }

}
