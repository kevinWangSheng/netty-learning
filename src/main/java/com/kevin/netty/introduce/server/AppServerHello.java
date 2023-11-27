package com.kevin.netty.introduce.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author wang
 * @create 2023-2023-25-22:26
 */
@ChannelHandler.Sharable // 加上这个注解是为了保证线程安全
public class AppServerHello {

    private int port;

    public AppServerHello(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bs = new ServerBootstrap();
            // 对这个连接进行基础的配置，包括地址、通道、处理handler等
            bs.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HandlerServerHello());
                        }
                    });

            ChannelFuture future = bs.bind().sync();
            System.out.println("服务器在"+future.channel().localAddress()+"监听");

            //进行阻塞操作，直到线程结束 ,这里是进行的同步操作
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync(); //关闭EventLoopGroup并释放所有资源，包括所有创建的线程
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new AppServerHello(18080).run();
    }
}
