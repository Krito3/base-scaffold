package com.krito3.base.scaffold.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kno.ci
 * @description:
 * @date 2022/3/11 上午10:59
 */
public class IpUtil {
    /**
     * 本机地址
     */
    private static final String DEFAULT_LOCAL_HOST = "127.0.0.1";

    /**
     * 未知IP地址
     */
    private static final String UNKNOWN_IP = "unknown";

    private IpUtil() {
    }

    /**
     * 获取客户端真实IP
     *
     * @return
     */
    public static String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            String[] ipArr = ip.split(",");
            for (int i = 0; i < ipArr.length; i++) {
                if (!ipArr[i].trim().equals(UNKNOWN_IP)) {
                    ip = ipArr[i].trim();
                    break;
                }
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            return DEFAULT_LOCAL_HOST;
        }
        return ip;
    }

}
