package com.shinelon.httpserver.handler;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinelon.httpserver.enums.CodeEnum;
import com.shinelon.httpserver.route.Route;
import com.shinelon.httpserver.service.BizLogic;
import com.shinelon.httpserver.utils.ChannelHandlerUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;

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
        BizLogic adLogic = Route.lookFor(path);
        Object ret = adLogic.execute(ChannelHandlerUtil.parseParam(request));
        ChannelHandlerUtil.writeJsonResult(ctx, ret, CodeEnum.SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws UnsupportedEncodingException {
        logger.error(cause.getMessage(), cause);
        ChannelHandlerUtil.writeJsonResult(ctx, cause.getMessage(), CodeEnum.EXCEPTION);
    }

}
