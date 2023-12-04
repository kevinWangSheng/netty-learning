package com.kevin.netty.introduce.charcode.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wang
 * @create 2023-2023-04-11:51
 */
public class CharCodeServer {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bs = new ServerBootstrap();
        NioEventLoopGroup parent = new NioEventLoopGroup();
        NioEventLoopGroup child = new NioEventLoopGroup();
        try {

            bs.group(parent,child)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new MyCharCodeChannelInitializer());

            ChannelFuture future = bs.bind(7009).sync();
            System.out.println("发送消息，进行同步等待");
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
