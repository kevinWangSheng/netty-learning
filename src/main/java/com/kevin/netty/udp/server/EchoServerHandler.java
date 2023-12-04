package com.kevin.netty.udp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @author wang
 * @create 2023-2023-30-17:39
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<io.netty.channel.socket.DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf content = datagramPacket.copy().content();
        byte[] req = new byte[content.readableBytes()];
        content.readBytes(req);
        System.out.println("接收到的数据："+new String(req));

        channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("EchoServerHandler 接收到数据了,时间戳："+System.currentTimeMillis(), CharsetUtil.UTF_8),datagramPacket.sender()));
    }
}
