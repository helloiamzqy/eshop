package com.item.eshop.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUpload {
    public static String changeFileName(String name,String code){
        int index = name.lastIndexOf(".");
        return code+name.substring(index,name.length());
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
