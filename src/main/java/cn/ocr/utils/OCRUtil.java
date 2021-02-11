package cn.ocr.utils;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class OCRUtil {
    private static final String APP_ID = "22895192";
    private static final String API_KEY = "97xIkHRKg5Q7GdSEUcriZAO7";
    private static final String SECRET_KEY = "Sg6L5aFWOhCupw6MiDeL72w0Q9R7vSCo";
    private static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    public static final int GENERAL_BASIC = 0;
    public static final int ACCURATE_BASIC = 1;

    public static JSONObject getResultbasicGeneral(String imagePath) throws IOException {
        client.setConnectionTimeoutInMillis(2000);
        return client.basicGeneral(imagePath, new HashMap<>());//标准版

    }


    public static JSONObject getResultbasicAccurateGeneral(String imagePath) throws IOException {
        client.setConnectionTimeoutInMillis(2000);
        return client.basicAccurateGeneral(Util.readFileByBytes(imagePath), new HashMap<>());//高精度
    }

    /**
     *
     * @param imagePath  image文件路径
     * @param model     文字提取模式 0：标准  1：高精度
     * @return          响应JSON数据
     * @throws IOException
     */

    public static JSONObject getResult(String imagePath,int model) throws IOException {
        client.setConnectionTimeoutInMillis(2000);
        if (model == 0){
            return client.basicGeneral(Util.readFileByBytes(imagePath), new HashMap<>());
        }else {
            return client.basicAccurateGeneral(Util.readFileByBytes(imagePath), new HashMap<>());
        }
    }

    public static String hasErr(JSONObject jsonObject) {
        if (jsonObject.has("error_code")) { //请求失败
            String error_msg = jsonObject.getString("error_msg");
            String error_code = jsonObject.get("error_code").toString();
            switch (error_code) {
                case "17":
                    System.out.println(error_code + "____ " + error_msg + " 每天流量超限额 ");
                    break;
                case "18":
                    System.err.println(error_code + "____ " + error_msg + " QPS超限额 ");
                    break;
                default:
                    System.err.println("其他错误,请查看文档,错误代码为  " + error_code);
            }
            return error_code;
        }
        return null;
    }

}
