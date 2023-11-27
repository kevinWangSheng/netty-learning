package com.kevin.netty.introduce.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**服务器发送信息的基础类
 * @author wang
 * @create 2023-2023-25-22:22
 */
@ChannelHandler.Sharable // 保证线程安全
public class HandlerServerHello extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        System.out.println("服务端收到消息："+message.toString(CharsetUtil.UTF_8));

        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已经收到你的"+message.toString(CharsetUtil.UTF_8)+"了",CharsetUtil.UTF_8 ));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception,调用"+ctx.name()+"出错了,原因："+cause.getMessage());
        ctx.close();
    }
}
