package cn.ocr.utils;

import cn.ocr.bean.Students;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadJSONFromFile {
   private static  List<Students> arrayList = null;
    public static List<Students> readDataFromFile() throws IOException {

        arrayList = new ArrayList<>();
        File file = new File("src\\main\\resources\\json\\NumberAndName.json");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(read);
        String line = "";
        String temp = "";
        while ((temp = bufferedReader.readLine()) != null) {
            line += temp.trim();
        }
        read.close();
        bufferedReader.close();

        JSONArray list = new JSONArray(line);
        for (int i = 0; i<list.length(); i++){
            JSONObject jObject = list.getJSONObject(i);
            Students student = new Students();
            student.setId(jObject.getString("id"));
            student.setName(jObject.getString("keyWord"));
            arrayList.add(student);
        }
        return arrayList;
    }

    public static HashMap<String, String> getMap() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        List<Students> students = readDataFromFile();
        for(Students student : students){
            map.put(student.getName(),student.getId());
        }
        return map;
    }

}
