package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author zhong
 * @date 2023/5/8 0:11
 */
@WebService
public interface HelloWorld {
    @WebMethod
    String sayHello(String name);
}
