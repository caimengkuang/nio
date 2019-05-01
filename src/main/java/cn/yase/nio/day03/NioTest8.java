package cn.yase.nio.day03;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 直接缓冲实现零拷贝
 * @author yase
 * @create 2019-05-01
 */
public class NioTest8 {

    public static void main(String[] args) throws IOException {
        //将input.txt中的文件写到output.txt中
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        //直接缓冲
        ByteBuffer buffer = ByteBuffer.allocateDirect(4);

        while (true){
            buffer.clear();
            int read = inputChannel.read(buffer);

            if (-1 == read){
                //说明文件读取完毕
                break;
            }
            buffer.flip();
            System.out.println("flip after position:"+buffer.position());
            System.out.println("flip after limit:"+buffer.limit());

            outputChannel.write(buffer);
            System.out.println("write after position:"+buffer.position());
            System.out.println("write after limit:"+buffer.limit());
        }

        inputChannel.close();
        outputChannel.close();
    }

}
