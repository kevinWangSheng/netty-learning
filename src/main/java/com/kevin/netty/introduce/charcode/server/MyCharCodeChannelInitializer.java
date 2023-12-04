package com.kevin.netty.introduce.charcode.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author wang
 * @create 2023-2023-04-11:30
 */
public class MyCharCodeChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));

        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));

        ch.pipeline().addLast(new CharEncodeChannelHandler());
    }
}
