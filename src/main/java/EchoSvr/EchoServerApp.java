package EchoSvr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class EchoServerApp
{
    private final int port;

    public EchoServerApp(int port){
        this.port=port;
    }
    public static void main( String[] args ) throws InterruptedException
    {
        int port=8899;
        if(args.length!=1){
            System.out.println("Usage:"+ EchoServerApp.class.getSimpleName()+" <port>");
        }
        else{
            port=Integer.valueOf(args[0]);
        }

        new EchoServerApp(port).start();
    }

    private void start() throws InterruptedException {

        final EchoHandler serverHandler=new EchoHandler();

        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();

        try {
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
