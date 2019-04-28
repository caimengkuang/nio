package cn.yase.nio.day01;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将数据写入到NioTest3.txt中
 * @author yase
 * @create 2019-04-28
 */
public class NioDemo3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        //要写入的数据
        byte[] messages = "hello world nihao".getBytes();

        //将数据写入到Buffer中
        for (int i=0;i<messages.length;++i){
            byteBuffer.put(messages[i]);
        }

        //Buffer反转
        byteBuffer.flip();

        //将数据流向Channel
        channel.write(byteBuffer);

        fileOutputStream.close();
    }

}
