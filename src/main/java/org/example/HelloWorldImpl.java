package org.example;

import javax.jws.WebService;

/**
 * @author zhong
 * @date 2023/5/8 0:11
 */
@WebService(endpointInterface = "org.example.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}

