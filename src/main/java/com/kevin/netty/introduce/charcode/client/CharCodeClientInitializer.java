package com.kevin.netty.introduce.charcode.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wang
 * @create 2023-2023-04-12:12
 */
public class CharCodeClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new CharCodeClientHandler());
    }
}
