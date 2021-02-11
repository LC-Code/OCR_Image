package cn.ocr.main;

import cn.ocr.utils.ImageFile;
import cn.ocr.utils.OCRUtil;
import cn.ocr.utils.ReadJSONFromFile;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {


        HashMap<String, String> map = ReadJSONFromFile.getMap();
        List<File> errFileList = new ArrayList<>();  //失败文件集合
        int count = 0; //记录文件重命名数量
        boolean flag = true;
        int mateModel = 0;  //默认 标准模式
        int err = 0;
//        System.out.println(" 输入扫描文件夹路径 ");
//        Scanner in = new Scanner(System.in);
//        String path = in.nextLine();

//        List<File> imageFiles = ImageFile.getImageFiles(new File(path));
        List<File> imageFiles = ImageFile.getImageFiles(new File("D:\\Desktop\\报平安截图\\李闯组"));

        for (int i = 0; i < imageFiles.size(); i++) {
            if(mateModel == OCRUtil.GENERAL_BASIC){
                System.out.println(" ————————标准模式匹配——————— ");
            }else {
                System.out.println(" ——————-高进度模式匹配——————— ");
            }
            System.out.println("对文件 "+imageFiles.get(i).getName()+" 中的文字进行字符比对。。。。。 ");
            JSONObject jsonObject = OCRUtil.getResult(imageFiles.get(i).getAbsolutePath(),mateModel);
          //  System.out.println(jsonObject.toString(2));
            String s = OCRUtil.hasErr(jsonObject);
            if (s == null) {
                int core = JsonMateImage(imageFiles.get(i), map, jsonObject.getJSONArray("words_result"));
                switch (core) {
                    case -1:  //修改失败
                        mateModel = OCRUtil.GENERAL_BASIC;
                        errFileList.add(imageFiles.get(i));
                        break;
                    case 0:    //文件已存在
                        mateModel = OCRUtil.GENERAL_BASIC;
                        break;
                    case 1:    //文件名修改成功
                        count++;
                        mateModel = OCRUtil.GENERAL_BASIC;
                        break;
                    case 2:    //数据匹配失败
                        if(mateModel == OCRUtil.ACCURATE_BASIC){
                            errFileList.add(imageFiles.get(i));
                            mateModel = OCRUtil.GENERAL_BASIC;
                            break;
                        }

                        System.err.println("标准模式匹配失败，跟换高精度模式。。。。");
                        Thread.sleep(2000);
                        mateModel = OCRUtil.ACCURATE_BASIC;
                        i--;
                        break;
                }
            }else {
                if("18".equals(s)){
                    Thread.sleep(2000);
                }
                if("17".equals(s) && mateModel == OCRUtil.ACCURATE_BASIC){
                    mateModel = OCRUtil.GENERAL_BASIC;
                    errFileList.add(imageFiles.get(i));
                }
                if("17".equals(s) && mateModel == OCRUtil.GENERAL_BASIC){
                    System.err.println("文字识别超出额度，请第二天重试");
                    return;
                }
            }
        }
        Thread.sleep(2000);

        System.out.println("被搜索到的文件 = " + imageFiles.size());
        System.out.println("其中被修改的文件 = " + count);
        System.err.println("修改失败的文件 = " + errFileList.size());
        for (int i = 0; i < errFileList.size(); i++) {
            System.err.println(errFileList.get(i).getName());
        }
    }

    /**
     * @param file      image 文件对象
     * @param map       匹配数据集
     * @param jsonArray 图片文字识别数据
     * @return 1 文件名修改成功   -1 修改失败  0 文件已存在    2  匹配失败
     */
    private static int JsonMateImage(File file, HashMap<String, String> map, JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String words = jsonObject.getString("words");
            boolean contains = map.keySet().contains(words);
            if (contains) {
                String number = map.get(words);
                return ImageFile.renameWithImageFile(file, number + "_" + words);
            }
        }
        return 2;
    }
}
