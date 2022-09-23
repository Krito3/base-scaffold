package com.krito3.base.scaffold.common.utils;

/**
 * @author kno.ci
 * @description: 常量
 * @date 2022/2/12 下午4:04
 */
public interface BaseConstant {
    /**
     * 编码
     */
    String UTF_8 = "UTF-8";

    String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /**
     * 数据脱敏密钥
     */
    String SECURE_DATA="04e9958cbdf3e2be3bf38a84603bc542";

    /**
     * json
     */
    String RESPONSE_CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 默认用户密码
     */
    public static final String DEFAULT_PASSWORD = "000000";

    /**
     * redis 登陆 key
     */
    String LOGIN_KEY= "base:login:%s";
    String JWT_KEY= "jwtKey";

    /**
     * 用户角色key
     */
    String LOGIN_USER_ROLES = "user_roles";

    /**
     * 管理员code
     */
    String ADMINISTRATOR_CODE = "Administrator";

    String LOGIN_USER_PERMS = "user_perms";

    String ANONYMOUS_USER_CODE = "anonymousUser";
}
