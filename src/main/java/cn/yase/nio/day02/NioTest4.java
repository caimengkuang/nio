package cn.yase.nio.day02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yase
 * @create 2019-04-30
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {

        //将input.txt中的文件写到output.txt中
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");


        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(4);

        while (true){
            //没有clear()方法将会出现:死循环
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read: "+read);

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
