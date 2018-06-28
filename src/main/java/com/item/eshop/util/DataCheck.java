package com.item.eshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheck {        //邮箱账号格式验证

    public static boolean checkEmail(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(RULE_EMAIL);
        Matcher m = p.matcher(emaile);
        return m.matches();
    }

    public static String generateCode() {       //生成随机验证码和资源文件链接地址后缀
        String code = "";
        int rand = 0;
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        for(int i=0;i<20;i++) {
            rand = (int) (Math.random()*codeSequence.length);
            code += codeSequence[rand];
        }
        return  code;
    }

}

