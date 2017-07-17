package com.demo.util;

import com.demo.build.RMQFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenynjyy on 2017/7/17.
 */
public class CheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    public static void vhost() {
        try {
            logger.info("vhost is = " + RMQFactory.build().getVirtualHost());
        } catch (IOException e) {
        } catch (TimeoutException e) {
        }
    }

    public static void main(String[] args) {
        vhost();
    }

}
