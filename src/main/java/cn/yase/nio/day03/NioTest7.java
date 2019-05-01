package cn.yase.nio.day03;

import java.nio.ByteBuffer;

/**
 *
 * 只读Buffer，我们可以随时将一个普通Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
 * 但不能将一个只读Buffer转换为读写Buffer
 *
 * @author yase
 * @create 2019-05-01
 */
public class NioTest7 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for (int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }

        //获取只读buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

    }

}
