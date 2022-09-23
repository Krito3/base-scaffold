package com.krito3.base.scaffold.common.exception;

import java.io.Serializable;

/**
 * @author kno.ci
 * @description:
 * @date 2022/2/12 下午3:44
 */
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -8861184154578392459L;

    public BusinessException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }

    public BusinessException(String errMsg) {
        super(errMsg);
    }
}
