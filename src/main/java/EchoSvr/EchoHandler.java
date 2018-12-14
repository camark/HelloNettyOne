package EchoSvr;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.time.LocalTime;

@ChannelHandler.Sharable
public class EchoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf in=(ByteBuf)o;

        System.out.println(new StringBuilder().
                append("Server Recieved:").
                append(in.toString(CharsetUtil.UTF_8)).
                append(" at ").
                append(LocalTime.now().toString()).toString());

        channelHandlerContext.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }


}
