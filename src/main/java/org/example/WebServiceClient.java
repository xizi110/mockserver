package org.example;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * @author zhong
 * @date 2023/5/8 0:07
 */
public class WebServiceClient {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/hello?wsdl");
        QName qname = new QName("http://example.org/", "HelloWorldImplService");

        Service service = Service.create(url, qname);

        HelloWorld hello = service.getPort(HelloWorld.class);
        System.out.println(hello.sayHello("John Doe"));
    }
}
