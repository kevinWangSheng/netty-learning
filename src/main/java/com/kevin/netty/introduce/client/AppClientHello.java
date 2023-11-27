package com.kevin.netty.introduce.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**客户端启动类
 * @author wang
 * @create 2023-2023-25-22:06
 */
public class AppClientHello {

    private int port;

    private String host;

    public AppClientHello(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HandlerClientHello());
                        }
                    });
            ChannelFuture future = bs.connect().sync();
            // 发送信息
            future.channel().writeAndFlush(Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8));
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new AppClientHello(18080,"127.0.0.1").run();
    }
}
