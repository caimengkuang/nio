package cn.yase.nio.day06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author yase
 * @create 2019-05-06
 */
public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        //创建一个Channel、监听8899端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);



        while (true){
            int number = selector.select();
            System.out.println("number: "+number);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeys){

                final SocketChannel socketChannel;

                if (selectionKey.isAcceptable()){
                    //连接事件,获取客户端channel,将channel事件为OP_READ并注册到selector中
                    try {
                        ServerSocketChannel serverSocketChannelOne = (ServerSocketChannel)selectionKey.channel();
                        serverSocketChannelOne.configureBlocking(false);
                        socketChannel = serverSocketChannelOne.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);

                        //记录客户端信息到服务端，为了实现客户端分发
                        String key = "【"+UUID.randomUUID().toString()+"】";
                        clientMap.put(key,socketChannel);

                    } catch (IOException e) {

                    }
                }else if (selectionKey.isReadable()){
                    try {
                        socketChannel = (SocketChannel)selectionKey.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(512);

                        int count = socketChannel.read(readBuffer);

                        if (count > 0){
                            readBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(socketChannel+":"+receivedMessage);

                            String sendKey = null;
                            for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                if (socketChannel == entry.getValue()){
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }
                            //分发给各个客户端
                            for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                SocketChannel value = entry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((sendKey+":"+receivedMessage).getBytes());
                                writeBuffer.flip();
                                value.write(writeBuffer);


                            }

                        }
                    } catch (IOException e) {

                    }
                }
            }

            selectionKeys.clear();

        }


    }

}
