package cn.yase.nio.day03;

import java.nio.ByteBuffer;

/**
 *
 * 分割buffer
 *
 * 从一个buffer中指定起始位置、结束位置分割出新的buffer
 *
 * 在新buffer中进行操作都会影响原先的buffer
 *
 * @author yase
 * @create 2019-05-01
 */
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0;i < buffer.capacity();i++){
            buffer.put((byte)i);
        }

        //指定起始位置、结束位置
        buffer.position(2);
        buffer.limit(6);
        //获取新的buffer
        ByteBuffer sliceBuffer = buffer.slice();

        //修改新的buffer
        for (int i = 0;i < sliceBuffer.capacity();i++){
            byte b = sliceBuffer.get(i);
            b*=2;
            sliceBuffer.put(i,b);
        }

        //输出原先的buffer内容，发现原先的buffer内容改变
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }


}
