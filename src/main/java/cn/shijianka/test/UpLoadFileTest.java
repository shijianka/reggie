package cn.shijianka.test;

import org.junit.jupiter.api.Test;

public class UpLoadFileTest {
    @Test
    public void test1(){
        String fileName="testName.123";
        String substring = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(substring);

    }
}
