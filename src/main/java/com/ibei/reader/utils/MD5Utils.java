package com.ibei.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source,Long salt){
        char[] chars=source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i]=(char) (chars[i]+salt);
        }
        String target = new String(chars);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
