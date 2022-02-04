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
import server.natural.Utils;

//todo 汉化
public class BaseServer implements Runnable{
    public static Thread thread = null;
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
    //Init the chat server and bootstrap
    @Override
    public void run(){
        //Set thread for command "stopcs"
        this.thread = Thread.currentThread();
        try {
            if(Utils.isOpenChatServer){
                Thread.currentThread().setName("Chat2WithGroup-NioSocket-ChatServer");
                Bukkit.getLogger().info("Init thread pool on thread:"+Thread.currentThread().getName()+".");
                EventLoopGroup BaseWorker = new NioEventLoopGroup();
                EventLoopGroup WorkerGroup = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                Bukkit.getLogger().info("Bootstrapping Server.");
                //Init the bootstrapper
                bootstrap.group(BaseWorker, WorkerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(channel)
                        .option(ChannelOption.SO_KEEPALIVE, true);
                Bukkit.getLogger().info("Binding on:"+this.host+":"+this.port);
                //Bind port
                bootstrap.bind(this.host, this.port).sync();}
            //Init the event groups
        }catch (Exception e){e.printStackTrace();}
    }
}
