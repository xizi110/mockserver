package org.example;

import javax.xml.ws.Endpoint;

/**
 * @author zhong
 * @date 2023/5/8 0:07
 */
public class WebServiceServer {
    public static void main(String[] args) throws Exception {
        Endpoint.publish("http://localhost:8080/hello", new HelloWorldImpl());
    }
}
