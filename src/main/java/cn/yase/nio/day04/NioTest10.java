package cn.yase.nio.day04;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author yase
 * @create 2019-05-04
 */
public class NioTest10 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt","rw");

        FileChannel fileChannel = randomAccessFile.getChannel();
        //true共享锁、false排他锁
        FileLock fileLock = fileChannel.lock(3, 6, true);

        System.out.println("valid:"+fileLock.isValid());
        System.out.println("lock type:"+fileLock.isShared());
        fileLock.release();

        fileChannel.close();


    }

}
