package cn.ocr.test;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class Sample {
    public  final String APP_ID = "22895192";
    public  final String API_KEY ="97xIkHRKg5Q7GdSEUcriZAO7";
    public  final String SECRET_KEY = "Sg6L5aFWOhCupw6MiDeL72w0Q9R7vSCo";
    @Test
    public void function () {
        //初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        HashMap<String,String > options = new HashMap<>();
        options.put("probability", "true");
        options = null;

        //设置网络连接参数，，，，，，可以不设置

        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(6000);

        //设置代理服务器地址。。。。。可选http、socket 其一，，，也可不设置
//        client.setHttpProxy("proxy_host",proxy_port);
//        client.setSocketProxy("proxy_host",proxy_port);

        //设置log4j日志输出格式，若不设置，则使用默认配置，也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("api.log4j.conf","src\\main\\resources\\log\\log4j.properties");
//
//        File file = new File("src\\main\\resources\\log\\log4j.properties");
//        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        //接口调用
        String path = "D:\\Desktop\\报平安截图\\张奥组\\085417121_张奥.jpg";
        JSONObject res = client.basicAccurateGeneral(path, new HashMap<>());
        System.out.println("res = " + res.toString(2));
    }


}

