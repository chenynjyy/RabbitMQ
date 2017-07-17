package com.demo;

import com.demo.recv.BlockingComsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class BlockingClientLauncher {

    /**
     * 当阻塞监听程序启动时，生产者发送消息才能接收到
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        new BlockingComsumer().setQueue(UUID.randomUUID().toString()).setBindingKey("qq.*").go();
    }

}
