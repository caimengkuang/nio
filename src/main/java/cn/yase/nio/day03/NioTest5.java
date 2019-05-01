package cn.yase.nio.day03;

import java.nio.ByteBuffer;

/**
 * ByteBuffer类型化的put与get方法
 * @author yase
 * @create 2019-05-01
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        //放进去什么类型、取出来就是什么类型

        byteBuffer.putInt(15);
        byteBuffer.putDouble(13.2);
        byteBuffer.putChar('你');

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());

    }

}
