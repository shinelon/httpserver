package com.shinelon.httpserver.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinelon.httpserver.enums.CodeEnum;
import com.shinelon.httpserver.route.Route;
import com.shinelon.httpserver.service.AdLogic;
import com.shinelon.httpserver.utils.JsonResultUtil;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * ServerHandler.java
 * 
 * @author syq
 *
 *         2018年5月18日
 */
public class ServerHandler extends SimpleChannelInboundHandler<HttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        logger.debug("request:{}", request);
        String path = request.uri();
        AdLogic adLogic = Route.lookFor(path);
        Object ret = adLogic.execute(parseParam(request));
        writeResult(ctx, ret, CodeEnum.SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws UnsupportedEncodingException {
        logger.error(cause.getMessage(), cause);
        writeResult(ctx, cause.getMessage(), CodeEnum.EXCEPTION);
    }

    private Map<String, String> parseParam(HttpRequest request) {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> parameters = decoder.parameters();
        Map<String, String> params = new HashMap<>(16);
        Iterator<Entry<String, List<String>>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<String>> next = iterator.next();
            List<String> value = next.getValue();
            if (value != null) {
                params.put(next.getKey(), next.getValue().get(0));
            }
        }
        return params;
    }

    private void writeImg(ChannelHandlerContext ctx) throws IOException {
        File file = new File("D:/fr.jpg");
        byte[] imgBytes = null;
        try (InputStream is = new FileInputStream(file)) {
            imgBytes = IOUtils.toByteArray(is);
        }
        logger.info("img length,{}", imgBytes.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
                PooledByteBufAllocator.DEFAULT.directBuffer().writeBytes(imgBytes));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "image/gif");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void writeResult(ChannelHandlerContext ctx, Object result, CodeEnum codeEnum)
            throws UnsupportedEncodingException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, PooledByteBufAllocator.DEFAULT
                .directBuffer().writeBytes(JsonResultUtil.getJson(codeEnum, result).toJSONString().getBytes("utf-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
