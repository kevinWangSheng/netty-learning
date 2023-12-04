package com.kevin.netty.introduce.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wang
 * @create 2023-2023-27-13:13
 */
public class FirstServer {
    private int port;

    public FirstServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new FirstServer(11580).bind();
    }

    public void bind() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.group(parentGroup,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(NioChannelOption.SO_BACKLOG,128)
                    .childHandler(new MyChannelInitializer());

            ChannelFuture future = bs.bind(port).sync();
            // 进行同步阻塞，直到上面的请求处理完成之后才会进行释放
            future.channel().closeFuture().sync();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}
