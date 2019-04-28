package cn.yase.nio.day01;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 *
 * @author yase
 * @create 2019-04-28
 */
public class NioDemo1 {

    public static void main(String[] args) {
        //分配一个大小为10的缓存区，缓存区只能存放证书
        IntBuffer buffer = IntBuffer.allocate(10);

        //将数据写入Buffer中
        for (int i =0;i<buffer.capacity();i++){
            //生成随机数
            int randomNumber = new SecureRandom().nextInt(20);
            //将随机数存放到缓存区中
            buffer.put(randomNumber);
        }

        //反转
        buffer.flip();

        //从Buffer中读取数据
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

}
