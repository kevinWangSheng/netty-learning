package com.kevin.netty.introduce.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author wang
 * @create 2023-2023-04-18:13
 */
public class MyHttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            DefaultHttpRequest httpRequest = (DefaultHttpRequest) msg;
            System.out.println("the url is :"+httpRequest.getUri());
            System.err.println(msg);
        }

        if(msg instanceof HttpContent){
            LastHttpContent lastHttpContent = (LastHttpContent) msg;
            ByteBuf content = lastHttpContent.content();
            if(!(content instanceof EmptyByteBuf)){
                byte[] bytes = new byte[content.readableBytes()];
                content.readBytes(bytes);
                System.out.println("the content is :"+new String(bytes, CharsetUtil.UTF_8));
            }
        }

        String str = "新创建的一个http处理器";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, Unpooled.wrappedBuffer(str.getBytes(Charset.forName("UTF-8"))));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
        ctx.flush();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
