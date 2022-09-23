package com.krito3.base.scaffold.common.utils;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kno.ci
 * @description: 对称加密
 * @date 2022/2/13 下午6:22
 */
public class AESUtil {

    /**
     * 数据库存储手机号的时候。不存储明文
     *
     * @param phone 手机号
     * @return map： phoneNumber：133****4280  ， secure：加密后的密文，这2个字段都需要存到数据库
     */
    public static Map<String, String> securePhone(String phone) {
        Map<String, String> data = new HashMap<>();
        data.put("phoneNumber", DesensitizedUtil.mobilePhone(phone));
        String content = phone.substring(3, 7);
        String encrypt = encrypt(content, BaseConstant.SECURE_DATA.getBytes());
        data.put("secure", encrypt);
        return data;
    }

    public static Map<String, String> secureEmail(String email) {
        Map<String, String> data = new HashMap<>();
        try {
            data.put("email", Base64.getEncoder().encodeToString(email.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encrypt = encrypt(email, BaseConstant.SECURE_DATA.getBytes());
        data.put("secure", encrypt);
        return data;
    }

    public static void main(String[] args) {
//        Map<String, String> stringStringMap = AESUtil.securePhone("13311984280");
//        System.out.println("加密后"+stringStringMap);
//
//        //解密
//        String secure = AESUtil.decryptStr(stringStringMap.get("secure"), BaseConstant.SECURE_DATA.getBytes());
//        System.out.println("解密后："+secure);


        Map<String, String> stringStringMap = AESUtil.secureEmail("496234142@qq.com");
        System.out.println("加密后" + stringStringMap);
        //解密
        String secure = AESUtil.decryptStr(stringStringMap.get("secure"), BaseConstant.SECURE_DATA.getBytes());
        System.out.println("解密后：" + secure);

    }

    /**
     * 对称加密
     *
     * @param content 原文
     * @param key     密钥
     * @return
     */
    public static String encrypt(String content, byte[] key) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        return aes.encryptHex(content);
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param key     密钥
     * @return
     */
    public static String decryptStr(String content, byte[] key) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        return aes.decryptStr(content);
    }
}
