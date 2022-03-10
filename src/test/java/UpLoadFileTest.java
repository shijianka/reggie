import cn.shijianka.reggie.utils.SMSUtils;
import org.junit.jupiter.api.Test;

public class UpLoadFileTest {
    @Test
    public void test1(){
        String fileName="testName.123";
        String substring = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(substring);

    }
    @Test
    public void test2(){
        //测试String 强转Long
        String num = "123";
        Long result=(long) Integer.parseInt(num);
    }
    @Test
    public void test3(){
        String signName="阿里云短信测试";
        String templateCode="SMS_154950909";
        String phoneNumbers="15091542252";
        String param="9870";
        SMSUtils.sendMessage(signName,templateCode,phoneNumbers,param);
    }
    @Test
    public void test4(){

    }
}
