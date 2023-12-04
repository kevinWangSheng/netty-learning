package com.kevin.netty.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wang
 * @create 2023-2023-30-17:37
 */
public class EchoServer {
    public static void main(String[] args) {
        Bootstrap bs = new Bootstrap();

        try {
            bs.group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .handler(new EchoServerHandler());

            bs.bind(9999).channel().closeFuture().wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
