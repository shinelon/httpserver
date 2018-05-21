package com.shinelon.httpserver.handler;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinelon.httpserver.enums.CodeEnum;
import com.shinelon.httpserver.route.Route;
import com.shinelon.httpserver.utils.ChannelHandlerUtil;
import com.shinelon.httpserver.utils.JwtUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

/**
 * JwtSecurityHandler.java
 *
 * @author syq
 *
 *         2018年5月21日
 */
public class JwtSecurityHandler extends SimpleChannelInboundHandler<HttpRequest> {

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityHandler.class);

    private static String HEAD_AUTHORIZATION = "Authorization";

    /***
     * 不自动关闭channel ，需要手动处理
     */
    public JwtSecurityHandler() {
        super(false);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        boolean release = true;
        try {
            if (Route.getPathAuth().equals(request.uri())) {
                ChannelHandlerUtil.writeJsonResult(ctx, createJwt(), CodeEnum.SUCCESS);
            } else {
                if (continueHandle(ctx, request)) {
                    release = false;
                    ctx.fireChannelRead(request);
                }
            }
        } finally {
            if (release) {
                ReferenceCountUtil.release(request);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws UnsupportedEncodingException {
        logger.error(cause.getMessage(), cause);
        ChannelHandlerUtil.writeJsonResult(ctx, cause.getMessage(), CodeEnum.EXCEPTION);
    }

    private boolean continueHandle(ChannelHandlerContext ctx, HttpRequest request) throws UnsupportedEncodingException {
        String token = request.headers().get(HEAD_AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            ChannelHandlerUtil.writeJsonResult(ctx, "授权失败", CodeEnum.AUTH_REEOR);
            return false;
        }
        if (!JwtUtil.checkJwt(token)) {
            ChannelHandlerUtil.writeJsonResult(ctx, "授权失败", CodeEnum.AUTH_REEOR);
            return false;
        }
        return true;
    }

    private String createJwt() {
        return JwtUtil.createJwt();
    }

}
