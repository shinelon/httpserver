package com.shinelon.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinelon.httpserver.config.ServerProperty;
import com.shinelon.httpserver.handler.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * AdApplication.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
@SpringBootApplication
public class HttpserverApplication {

    private static final Logger logger = LoggerFactory.getLogger(HttpserverApplication.class);

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext cac = SpringApplication.run(HttpserverApplication.class, args);
        ServerProperty config = cac.getBean(ServerProperty.class);
        logger.info("server is starting at port:{}", config.getPort());
        initServer(config.getPort());
    }

    private static void initServer(int p) throws InterruptedException {
        // 接收客户端连接用默认线程数为cpu的2倍,
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            // 绑定端口 ,等待绑定成功
            ChannelFuture f = server.bind(p).sync();
            // 等待服务器退出
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("adserver shutdown....");
        }
    }

}
