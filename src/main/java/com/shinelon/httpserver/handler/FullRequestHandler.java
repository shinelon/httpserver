package com.shinelon.httpserver.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinelon.httpserver.enums.CodeEnum;
import com.shinelon.httpserver.utils.ChannelHandlerUtil;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class FullRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger logger = LoggerFactory.getLogger(FullRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        byte[] bytes = ByteBufUtil.getBytes(msg.content());
        logger.debug("msg:{} \ncontent:{}", msg, new String(bytes));
        ChannelHandlerUtil.writeJsonResult(ctx, "ok", CodeEnum.SUCCESS);
    }

}
