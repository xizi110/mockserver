package org.example;

import java.io.IOException;
import java.net.Socket;

/**
 * @author zhong
 * @date 2023/5/7 23:54
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8082);
        socket.getOutputStream().write("hello".getBytes());
        socket.close();
    }

}
