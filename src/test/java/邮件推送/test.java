package 邮件推送;

import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.teaopenapi.models.Config;

public class test {
    // This file is auto-generated, don't edit it. Thanks.

/*

import com.aliyun.tea.*;
import com.aliyun.dm20151123.*;
import com.aliyun.dm20151123.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
*/



        /**
         * 使用AK&SK初始化账号Client
         *
         * @param accessKeyId
         * @param accessKeySecret
         * @return Client
         * @throws Exception
         */
        public static com.aliyun.dm20151123.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
            Config config = new Config()
                    // 您的AccessKey ID
                    .setAccessKeyId(accessKeyId)
                    // 您的AccessKey Secret
                    .setAccessKeySecret(accessKeySecret);
            // 访问的域名
            config.endpoint = "dm.aliyuncs.com";
            return new com.aliyun.dm20151123.Client(config);
        }

      /*  public static void main(String[] args_) throws Exception {
            java.util.List<String> args = java.util.Arrays.asList(args_);
            com.aliyun.dm20151123.Client client = test.createClient("accessKeyId", "accessKeySecret");
            SingleSendMailRequest singleSendMailRequest = new SingleSendMailRequest()
                    .setAccountName("admin@shijianka.cn")
                    .setAddressType(1)
                    .setTagName("code")
                    .setReplyToAddress(true)
                    .setToAddress("shijianka@outlook.com")
                    .setSubject("验证码")
                    .setReplyAddress("shijianka@foxmail.com")
                    .setReplyAddressAlias("时间卡")
                    .setClickTrace("1")
                    .setFromAlias("admin")
                    .setTextBody("邮件text正文，您的验证码为：123456")
                    .setHtmlBody("邮件html正文");
            // 复制代码运行请自行打印 API 的返回值
            client.singleSendMail(singleSendMailRequest);
        }*/
    }

