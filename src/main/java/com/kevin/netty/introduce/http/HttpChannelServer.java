package com.kevin.netty.introduce.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wang
 * @create 2023-2023-04-18:25
 */
public class HttpChannelServer {
    public static void main(String[] args) {
        ServerBootstrap bs = new ServerBootstrap();

        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();

        try {
            bs.group(parent,child)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new MyChannelHttpServerInitializer());

            ChannelFuture future = bs.bind(7369).sync();

            System.out.println("等待客户端发送消息");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
