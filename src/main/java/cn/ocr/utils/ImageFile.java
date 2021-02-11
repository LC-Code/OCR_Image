package cn.ocr.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageFile {

    public static List<File> getImageFiles(File document){
        List<File> fileList = new ArrayList<>();
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String fileName = pathname.getName();
                int indexOf = fileName.lastIndexOf(".");
                if(indexOf > 0 && indexOf < fileName.length()-1){
                    String suffix = fileName.substring(indexOf + 1);
                    if("jpg".equals(suffix) || "png".equals(suffix)){
                        return true;
                    }
                    return false;
                }
                return false;
            }
        };
        fileList.addAll(document.list().length >0 ? Arrays.asList(document.listFiles(fileFilter)) : null);
        File[] files = document.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory() && files[i].list().length>0){
                List<File> list = Arrays.asList(files[i].listFiles(fileFilter));
                fileList.addAll(list);
            }
        }
        return fileList;
    }

    public static int renameWithImageFile(File oldName, String newName) {
        String absolutePath = oldName.getAbsolutePath();
        String name = oldName.getName();
        int index_01 = absolutePath.lastIndexOf("\\");
        int index_02 = absolutePath.lastIndexOf(".");
        String substring = absolutePath.substring(index_01+1, index_02);
        String replace = absolutePath.replace(substring, newName);
        File newNameFile = new File(replace);

        System.out.println("文件 " + name+" 正在被检测测是否需要修改。。。。");
        if (!newNameFile.exists()) {
            boolean b = oldName.renameTo(newNameFile);
            if(b){
                System.out.println("文件 " +name + "修改为 "+ newNameFile.getName());
                return 1;
            }
            return -1;
        }else {
            System.out.println("文件 " + name+" 已存在或不需要修改");
            return 0;
        }
    }

}
