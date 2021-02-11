package cn.ocr.test;

import cn.ocr.utils.ImageFile;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class FileListTest {

    @Test
    public void function(){
        List<File> imageFiles = ImageFile.getImageFiles(new File("D:\\Desktop\\报平安截图"));
        for (int i = 0; i < imageFiles.size(); i++) {
            System.out.println("imageFiles = " + imageFiles.get(i).getAbsolutePath());
        }
        System.out.println(imageFiles.size());
    }
}
