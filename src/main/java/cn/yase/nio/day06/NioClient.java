package cn.yase.nio.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yase
 * @create 2019-05-06
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeys){
                if (selectionKey.isConnectable()) {

                    try {
                        //是否可连接事件
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();

                        //连接是否进行状态
                        if (clientChannel.isConnectionPending()) {
                            //连接成功建立
                            clientChannel.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                            writeBuffer.flip();
                            clientChannel.write(writeBuffer);

                            //等待用户输入
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true) {
                                    writeBuffer.clear();

                                    InputStreamReader input = new InputStreamReader(System.in);
                                    BufferedReader br = new BufferedReader(input);

                                    String sendMessage = br.readLine();

                                    writeBuffer.put(sendMessage.getBytes());
                                    writeBuffer.flip();
                                    clientChannel.write(writeBuffer);
                                }
                            });
                        }


                        clientChannel.register(selector, SelectionKey.OP_READ);

                    } catch (Exception e) {

                    }

                } else if (selectionKey.isReadable()) {
                    try {
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);

                        int count = clientChannel.read(readBuffer);

                        if (count > 0) {

                            String receivedMessage = new String(readBuffer.array(), 0, count);
                            System.out.println(receivedMessage);
                        }

                    } catch (IOException e) {

                    }
                }
            }

            selectionKeys.clear();
        }
    }

}
