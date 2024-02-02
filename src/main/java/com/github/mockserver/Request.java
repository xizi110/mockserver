package com.github.mockserver;

import java.util.List;

/**
 * @author xizi
 * @date 2024/2/1 11:29
 * @description
 */
public class Request {

    private List<String> headers;

    private List<String> parameters;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", parameters=" + parameters +
                '}';
    }
}
