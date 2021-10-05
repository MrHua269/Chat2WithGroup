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
public class BaseServer extends Thread{
    ChannelInitializer<SocketChannel> channel = new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) {
            System.out.println("initChannel ch:" + ch);
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
    public void run(){
        try {
            Bukkit.getLogger().info("Init Thread pool.........");
            EventLoopGroup BaseWorker = new NioEventLoopGroup();
            EventLoopGroup WorkerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            Bukkit.getLogger().info("Bootstrapping.........");
            bootstrap.group(BaseWorker, WorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channel)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            Bukkit.getLogger().info("Binding.........");
            bootstrap.bind(this.host, this.port).sync();
        }catch (Exception e){e.printStackTrace();}
    }
}
