package EchoSvr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class EchoClentApp
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello form EchoClient!" );

        new EchoClentApp("127.0.0.1",8899).start();



    }

    private void start() throws InterruptedException {

        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        Bootstrap serverBootstrap=new Bootstrap();

        try {
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new EchoClientHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    private String host;
    private int port;

    public EchoClentApp(String host, int port){
        this.host=host;
        this.port=port;
    }


}
