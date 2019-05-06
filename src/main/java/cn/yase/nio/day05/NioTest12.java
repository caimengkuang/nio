package cn.yase.nio.day05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yase
 * @create 2019-05-05
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {

        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        //构造Selector
        Selector selector = Selector.open();
        //将多个端口注册到selector，使得一个selector可以监听多个端口号
        for (int i=0;i<ports.length;i++) {
            //打开一个Channel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置不阻塞
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();

            InetSocketAddress address = new InetSocketAddress(ports[i]);
            //绑定操作
            serverSocket.bind(address);

            //将该channel注册到selector上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口:"+ports[i]);
        }

        while (true){
            int numbers = selector.select();
            System.out.println("numbers: "+numbers);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectedKeys: "+selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){

                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()){
                    //如果为连接事件
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);

                    //将该Channle从连接事件集合中删除
                    iterator.remove();

                    System.out.println("获取客户端连接: "+socketChannel);
                }else if (selectionKey.isReadable()){
                    //如果为读事件
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                    int bytesRead = 0;
                    while (true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();

                        int read = socketChannel.read(byteBuffer);

                        if (read <= 0){
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        bytesRead += read;
                    }

                    System.out.println("读取: "+bytesRead+",来自于:"+socketChannel);

                    iterator.remove();
                }

            }

        }


    }

}
