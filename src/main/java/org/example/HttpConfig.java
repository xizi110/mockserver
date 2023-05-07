package org.example;

import java.util.List;

/**
 * @author zhong
 * @date 2023/5/6 21:22
 */
public class HttpConfig {

    /**
     * 端口
     */
    private int port;
    private List<HttpApi> api;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<HttpApi> getApi() {
        return api;
    }

    public void setApi(List<HttpApi> api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return "HttpConfig{" +
                "port=" + port +
                ", api=" + api +
                '}';
    }
}
