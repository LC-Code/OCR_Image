package cn.ocr.test;

import cn.ocr.bean.Students;
import cn.ocr.utils.ReadJSONFromFile;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class JsonUtilTest {

    @Test
    public void function() throws IOException {
        List<Students> students = ReadJSONFromFile.readDataFromFile();
        System.out.println("students = " + students);
    }
}
