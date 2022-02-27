package co.novau233.socketServer;

import co.novau233.socketServer.Handlers.CacheManager;
import co.novau233.socketServer.Handlers.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.bukkit.Bukkit;

import java.util.concurrent.atomic.AtomicBoolean;
/*
 * @author Novau233(wangxyper)
 */
public class SocketServer extends Thread{
    public final String host;
    public final int port;
    public SocketServer(String host,int port){
        this.host=host;
        this.port=port;
        this.setDaemon(true);
        this.setPriority(5);
        this.setName("NChat-Main-Thread");
    }
    public ChannelInitializer<SocketChannel> channel = new ChannelInitializer<SocketChannel>(){
        @Override
        public void initChannel(SocketChannel ch) {
            ch.pipeline()
                    .addLast("encoder", new ObjectEncoder())
                    .addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                    .addLast("handler", new MessageHandler());
        }
    };
    public AtomicBoolean started = new AtomicBoolean(false);
    private ServerBootstrap serverBootstrap = null;

    public static void main(String[] args) throws InterruptedException {
        //The test method
        new SocketServer("0.0.0.0", 222).start();
        Thread.sleep(Long.MAX_VALUE);
    }
    public void run(){
        CacheManager.load();
        try {
            this.started.set(true);
            //初始化-1
            EventLoopGroup BaseWorker = new NioEventLoopGroup();
            EventLoopGroup WorkerGroup = new NioEventLoopGroup();
            this.serverBootstrap = new ServerBootstrap();
            //初始化-2
            this.serverBootstrap.group(BaseWorker, WorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channel)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_KEEPALIVE, true);
            Bukkit.getLogger().info("Binding on:" + this.host + ":" + this.port);
            //绑定端口
            this.serverBootstrap.bind(this.host, this.port).sync();
            //Init the event groups
        } catch (Exception e) {
            //处理异常
            if(!(e instanceof InterruptedException)){
                e.printStackTrace();
            }
            this.started.set(false);
        }

    }
}
