//package com.krito3.base.scaffold.common.exception;
//
//
//import com.krito3.base.scaffold.common.result.IResultCode;
//
///**
// * 自定义API异常
// * Created by macro on 2020/2/27.
// */
//public class ApiException extends RuntimeException {
//    private IResultCode errorCode;
//
//    public ApiException(IResultCode errorCode) {
//        super(errorCode.getMessage());
//        this.errorCode = errorCode;
//    }
//
//    public ApiException(String message) {
//        super(message);
//    }
//
//    public ApiException(Throwable cause) {
//        super(cause);
//    }
//
//    public ApiException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public IResultCode getErrorCode() {
//        return errorCode;
//    }
//}
