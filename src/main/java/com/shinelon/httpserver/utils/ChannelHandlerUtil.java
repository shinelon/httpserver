package com.shinelon.httpserver.utils;

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

import com.shinelon.httpserver.enums.CodeEnum;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

/**
 * ChannelHandlerUtil.java
 *
 * @author syq
 *
 *         2018年5月21日
 */
public class ChannelHandlerUtil {

    /***
     * 解析请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseParam(HttpRequest request) {
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

    public static Map<String, String> parsePostParm(FullHttpRequest request) throws IOException {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
        List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
        Map<String, String> params = new HashMap<>(16);
        for (InterfaceHttpData parm : parmList) {
            Attribute data = (Attribute) parm;
            params.put(data.getName(), data.getValue());
        }
        return params;
    }

    /***
     * 返回图片结果
     *
     * @param ctx
     * @throws IOException
     */
    public static void writeImgResult(ChannelHandlerContext ctx) throws IOException {
        File file = new File("D:/fr.jpg");
        byte[] imgBytes = null;
        try (InputStream is = new FileInputStream(file)) {
            imgBytes = IOUtils.toByteArray(is);
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
                PooledByteBufAllocator.DEFAULT.directBuffer().writeBytes(imgBytes));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "image/gif");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /***
     * channle 返回JSON 数据
     *
     * @param ctx
     * @param result
     * @param codeEnum
     * @throws UnsupportedEncodingException
     */
    public static void writeJsonResult(ChannelHandlerContext ctx, Object result, CodeEnum codeEnum)
            throws UnsupportedEncodingException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, PooledByteBufAllocator.DEFAULT
                .directBuffer().writeBytes(JsonResultUtil.getJson(codeEnum, result).toJSONString().getBytes("utf-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
