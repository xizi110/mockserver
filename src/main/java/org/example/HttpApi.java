package org.example;

import java.util.Map;

/**
 * @author zhong
 * @date 2023/5/6 20:53
 */
public class HttpApi {

    /**
     * uri
     */
    private String uri;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 响应头
     */
    private Map<String, String> responseHead;
    /**
     * 响应体
     */
    private String responseBody;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(Map<String, String> responseHead) {
        this.responseHead = responseHead;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "HttpConfig{" +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", responseHead=" + responseHead +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
