package cn.yase.nio.day02;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author yase
 * @create 2019-04-30
 */
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("buffer.capacity() = "+buffer.capacity());

        for (int i=0;i < 5 ;i++){
            int nextInt = new SecureRandom().nextInt(20);
            System.out.println(buffer.position());
            buffer.put(nextInt);
        }

        //10
        System.out.println("before flip limit:"+buffer.limit());

        buffer.flip();

        //5
        System.out.println("after flip limit:"+buffer.limit());

        while (buffer.hasRemaining()){
            System.out.println("position:"+buffer.position());
            //5
            System.out.println("limit:"+buffer.limit());
            //10
            System.out.println("capacity:"+buffer.capacity());

            System.out.println(buffer.get());
        }

    }

}
