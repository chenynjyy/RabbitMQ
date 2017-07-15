package com.demo.build;

import com.demo.Client;
import com.demo.send.DirectProducer;
import com.demo.send.FanoutProducer;
import com.demo.send.TopicProducer;
import com.oracle.jrockit.jfr.Producer;

/**
 * Created by chenynjyy on 2017/7/15 0015.
 */
public class SendFactory {

    enum Status {}

    public static Client build() {

        return null;
    }

    public static DirectProducer directBuild() {

        return null;
    }

    public static FanoutProducer fanoutBuild() {

        return null;
    }

    public static TopicProducer topicBuild() {

        return null;
    }

}
