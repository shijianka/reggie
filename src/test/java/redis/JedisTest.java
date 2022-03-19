package redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisTest {
    @Test
    public void jedis(){
     /*   //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //执行操作
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(n->{
            System.out.println(n);
        });
        //关闭连接
        jedis.close();*/
    }
}
