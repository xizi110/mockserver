package com.github.mockserver;

import java.util.List;

/**
 * @author xizi
 * @date 2024/2/1 11:29
 * @description
 */
public class Response {

    private List<Header> headers;
    private String content;

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Response{" +
                "headers=" + headers +
                ", content='" + content + '\'' +
                '}';
    }
}
