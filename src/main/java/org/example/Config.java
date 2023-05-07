package org.example;

import java.util.List;

/**
 * @author zhong
 * @date 2023/5/6 20:50
 */
public class Config {

    private List<HttpConfig> http;

    public List<HttpConfig> getHttp() {
        return http;
    }

    public void setHttp(List<HttpConfig> httpConfigs) {
        this.http = httpConfigs;
    }

    private List<SocketConfig> socket;

    public List<SocketConfig> getSocket() {
        return socket;
    }

    public void setSocket(List<SocketConfig> socketConfigs) {
        this.socket = socketConfigs;
    }

    @Override
    public String toString() {
        return "Config{" +
                "http=" + http +
                ", socket=" + socket +
                '}';
    }
}
