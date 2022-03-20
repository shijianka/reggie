package cn.shijianka.reggie.utils;

import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.teaopenapi.models.Config;

public class EmailUtils {

    /**
     * 发送邮件工具类
     * @param toAddress 目标地址
     * @param subject 邮件主题
     * @param textBody 邮件正文
     */
    public static void sendEmail(String toAddress,String subject,String textBody){
        com.aliyun.dm20151123.Client client = EmailUtils.createClient("LTAI5tKCgKUk4HzpiBRKuSiF", "rQJrdvC6Mnnq06gvT0ZxmDizpiAOch");
        SingleSendMailRequest singleSendMailRequest = new SingleSendMailRequest()
                .setAccountName("admin@shijianka.cn") //发信地址
                .setAddressType(1)  //0：发信地址随机 1：用确定的地址
                .setTagName("code")  //标签
                .setReplyToAddress(true)  //使用管理台中配置的回信地址
//                .setToAddress("zl1437850032@gmail.com") //目标地址
                .setToAddress(toAddress) //目标地址
                .setSubject(subject)  //邮件主题
                .setReplyAddress("shijianka@foxmail.com") //回信地址
                .setReplyAddressAlias("时间卡")    //回信地址别名
                .setClickTrace("0")     //打开数据追踪功能(1)
                .setFromAlias("admin")  //发信人昵称
                .setTextBody(textBody) ;
//                .setHtmlBody("邮件html正文");
        // 复制代码运行请自行打印 API 的返回值
        try {
            client.singleSendMail(singleSendMailRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dm20151123.Client createClient(String accessKeyId, String accessKeySecret) {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dm.aliyuncs.com";
        try {
            return new com.aliyun.dm20151123.Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
