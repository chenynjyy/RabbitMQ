package com.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 网上摘的
 * http://www.blogjava.net/qbna350816/archive/2016/08/02/431433.aspx
 */
public class SslSend {

    public static void main(String[] args) throws Exception {
        char[] keyPassphrase = "MySecretPassword".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream("/path/to/client/keycert.p12"), keyPassphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);
        char[] trustPassphrase = "rabbitstore".toCharArray();
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream("/path/to/trustStore"), trustPassphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);
        SSLContext c = SSLContext.getInstance("TLSv1.1");
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5671);
        factory.useSslProtocol(c);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare("rabbitmq-java-test", false, true, true, null);
        channel.basicPublish("", "rabbitmq-java-test", null, "Hello, World".getBytes());
        GetResponse chResponse = channel.basicGet("rabbitmq-java-test", false);
        if(chResponse == null) {
            System.out.println("No message retrieved");
        } else {
            byte[] body = chResponse.getBody();
            System.out.println("Recieved: " + new String(body));
        }
        channel.close();
        conn.close();
    }
}
