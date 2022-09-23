package com.krito3.base.scaffold.common.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.util.DigestUtils;


/**
 * @author kno.ci
 * @description: md5 必须要加 salt
 * @date 2022/2/13 下午1:17
 */
public class MD5Util {

    /**
     * 根据密码和盐 生成 32 位 加密字符串
     *
     * @param password
     * @param salt
     * @return
     */
    public static String core(String password, String salt) {
        String firstMi = DigestUtils.md5DigestAsHex(password.getBytes());
        String jieStr = firstMi.substring(5, 15);
        String newStr = salt + firstMi + jieStr;
        return DigestUtils.md5DigestAsHex(newStr.getBytes());
    }

    public static void main(String[] args) {
        String salt= IdUtil.getSnowflakeNextIdStr();
        System.out.println(salt);
        System.out.println(core("123456", salt));
    }

}
