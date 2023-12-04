package com.kevin.netty.introduce.charcode.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author wang
 * @create 2023-2023-04-11:56
 */
public class CharCodeClient {

    public static void main(String[] args) {
        Bootstrap client = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {

            client.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1",7009))
                    .handler(new CharCodeClientInitializer());

            ChannelFuture future = client.connect().sync();
            System.out.println("客户端进行连接中");
            future.channel().writeAndFlush("hello world");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
