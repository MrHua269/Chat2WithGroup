package server.natural.ChatServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.bukkit.Bukkit;
public class BaseServer implements Runnable{
    ChannelInitializer<SocketChannel> channel = new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) {
            System.out.println("Init channel:" + ch);
            ch.pipeline()
                    .addLast("decoder", new StringDecoder())
                    .addLast("encoder", new StringEncoder())
                    .addLast("handler", new ConnectHandler());
        }
    };
    private String host;
    private int port;
    public BaseServer(String Host,int Port){
        this.host = Host;
        this.port = Port;
    }
    @Override
    public void run(){
        try {
            Thread.currentThread().setName("Chat2WithGroup-NioSocket-ChatServer");
            Bukkit.getLogger().info("Init thread pool on thread:"+Thread.currentThread().getName()+".");
            EventLoopGroup BaseWorker = new NioEventLoopGroup();
            EventLoopGroup WorkerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            Bukkit.getLogger().info("Bootstrapping Server.");
            bootstrap.group(BaseWorker, WorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channel)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            Bukkit.getLogger().info("Binding on:"+this.host+":"+this.port);
            bootstrap.bind(this.host, this.port).sync();
        }catch (Exception e){e.printStackTrace();}
    }
}
