package cn.yase.nio.day04;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yase
 * @create 2019-05-04
 */
public class NioTest9 {

    public static void main(String[] args) throws IOException {

        //在内存中操作文件内容,原先NioTest9.txt的内容为 helloworld,
        //运行后改为aelboworld

        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');

        randomAccessFile.close();
    }

}
