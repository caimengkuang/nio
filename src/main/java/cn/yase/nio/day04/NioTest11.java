package cn.yase.nio.day04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *
 * 关于Buffer的Scattering与Gathering
 *
 * 打开终端，在mac上执行 nc localhost 8899
 * @author yase
 * @create 2019-05-04
 */
public class NioTest11 {

    public static void main(String[] args) throws IOException {
        //打开一个Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //定义一个端口号
        InetSocketAddress address = new InetSocketAddress(8899);
        //将该端口号绑定到Channel中
        serverSocketChannel.socket().bind(address);

        int messaegLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        //结束客户端的信息
        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            int bytesRead = 0;

            //将channle中的信息读到buffer中
            //如果读到的信息要是能小于3个buffer总的长度
            //将数据依次按照buffer1、buffer2、buffer3 的大小写入
            while (bytesRead < messaegLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead: "+bytesRead);

                Arrays.stream(buffers)
                        .map(byteBuffer -> "position: "+byteBuffer.position() + ",limit: "+byteBuffer.limit())
                        .forEach(System.out::println);
            }

            //执行flip操作
            Arrays.asList(buffers).forEach(Buffer::flip);

            //将buffer写到channle中
            long bytesWritten = 0;
            while (bytesWritten < messaegLength) {
                long r = socketChannel.write(buffers);

                bytesWritten += r;
            }

            Arrays.asList(buffers).forEach(Buffer::clear);

            System.out.println("bytesRead: "+bytesRead
                    + ", bytesWritten: "+bytesWritten
                    +", messaegLength: "+messaegLength);
        }
    }

}
