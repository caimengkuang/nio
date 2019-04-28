package cn.yase.nio.day01;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取外部文件
 *
 * 先创建一个Niotest2.txt文件，内容: hello world welcome
 * @author yase
 * @create 2019-04-28
 */
public class NioDemo2 {

    public static void main(String[] args) throws IOException {

        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        //获取Channel对象
        FileChannel channel = fileInputStream.getChannel();

        //Buffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);

        //将文件中的内容读到Buffer中
        channel.read(byteBuffer);

        //Buffer反转
        byteBuffer.flip();

        //从buffer中读取出数据
        while (byteBuffer.remaining() > 0){

            byte b = byteBuffer.get();

            System.out.println("Character: "+(char)b);
        }

        //将通道关闭
        fileInputStream.close();

    }

}
