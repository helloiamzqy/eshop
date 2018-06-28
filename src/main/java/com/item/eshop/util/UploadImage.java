package com.item.eshop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class UploadImage {
    @Autowired
    public String uploadImage(MultipartFile file, Integer imagetype,String path,String address) {
        String image = "";
        switch (imagetype) {
            case 0:
                image = "avater";  //头像
            case 1:
                image = "photo";   //身份证照片
                break;
            case 2:
                image = "good";  //商品图片
                break;
            case 3:
                image = "recommend";   //推荐栏图片
                break;
        }
        String fileName = file.getOriginalFilename();
        String code = DataCheck.generateCode();
        String pathname = FileUpload.changeFileName(fileName, code);
        String filePath = path +"/"+ image + "/";
        try {
            FileUpload.uploadFile(file.getBytes(), filePath, pathname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = address +"/"+ image + "/" + pathname;
        return url;
    }
}
