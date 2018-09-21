package com.example.atmosphere.demowebsocket;

import com.example.atmosphere.demowebsocket.handler.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.io.File;
import java.security.cert.CertificateException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class WebsocketServer {
    private static final int PORT = 8808;
    private static final String CHAT_ROOM = "/chat";
    private CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        new WebsocketServer().start();
    }

    public void start() {
        EventLoopGroup parentLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup processLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        SslContext sslCtx = createSslContext();
        serverBootstrap.group(parentLoopGroup, processLoopGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new WebSocketChannelInitializer(CHAT_ROOM, sslCtx));

        try {
            Channel channel = serverBootstrap.bind(PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            processLoopGroup.shutdownGracefully();
            parentLoopGroup.shutdownGracefully();
        }
    }


    private SslContext createSslContext() {
        File keyCert;
        File key;
        SslContext sslContext;
        try {
            SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
            keyCert = selfSignedCertificate.certificate();
            key = selfSignedCertificate.privateKey();
            sslContext = SslContextBuilder.forServer(keyCert, key).build();
        } catch (CertificateException | SSLException e) {
            log.error("self sign certificate fail");
            return null;
        }
        return sslContext;
    }
}
