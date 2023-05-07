package org.example;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhong
 * @date 2023/5/6 20:46
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final HttpConfig config;
    private Map<String, HttpApi> apiMap;

    public HttpServerHandler(HttpConfig config) {
        this.config = config;
        List<HttpApi> apiList = config.getApi();
        apiMap = apiList.stream().collect(Collectors.toMap(httpApi -> httpApi.getMethod() + "$" + httpApi.getUri(), Function.identity()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        String method = request.method().name();
        HttpApi api = apiMap.get(method + "$" + uri);
        System.out.println("method:" + method + ",uri:" + uri);
        if (api == null) {
            api = apiMap.get(null + "$" + uri);
        }
        if (!uri.equals("/helloworld")) {
            if (api != null) {
                sendResponse(ctx, HttpResponseStatus.OK, api);
            } else {
                sendError(ctx, HttpResponseStatus.NOT_FOUND);
            }
        }else {
            // webservice请求，状态码不能返回OK
            sendResponse(ctx, HttpResponseStatus.OK, api);

        }
    }

    private void sendResponse(ChannelHandlerContext ctx, HttpResponseStatus status, HttpApi api) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        response.content().writeBytes(api.getResponseBody().getBytes(CharsetUtil.UTF_8));

        Map<String, String> responseHead = api.getResponseHead();
        for (Map.Entry<String, String> entry : responseHead.entrySet()) {
            response.headers().set(entry.getKey(), entry.getValue());
        }
        response.headers().set("Content-Length", response.content().readableBytes());
        ctx.writeAndFlush(response);
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
