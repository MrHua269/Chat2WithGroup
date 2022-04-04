package co.novau233.socketServer.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bukkit.event.Listener;
import java.util.HashMap;

public class SocketHandler extends SimpleChannelInboundHandler<HashMap> implements Listener {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HashMap packet) {AsyncEventHandler.handleMessage(channelHandlerContext.channel(),packet);}
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        AsyncEventHandler.onChannelRemoved(ctx.channel());
        ctx.fireChannelUnregistered();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        AsyncEventHandler.onChannelRemoved(ctx.channel());
        ctx.fireExceptionCaught(cause);
    }
}
