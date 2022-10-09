//package com.krito3.base.scaffold.common.exception;
//
//import com.krito3.base.scaffold.common.result.ResultVO;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 全局异常处理
// * Created by macro on 2020/2/27.
// */
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ResponseBody
//    @ExceptionHandler(value = ApiException.class)
//    public ResultVO handle(ApiException e) {
//        if (e.getErrorCode() != null) {
//            return ResultVO.fail(e.getErrorCode());
//        }
//        return ResultVO.fail(e.getMessage());
//    }
//
//    @ResponseBody
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResultVO handleValidException(MethodArgumentNotValidException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        String message = null;
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = bindingResult.getFieldError();
//            if (fieldError != null) {
//                message = fieldError.getField()+fieldError.getDefaultMessage();
//            }
//        }
//        return ResultVO.fail(message);
//    }
//
//    @ResponseBody
//    @ExceptionHandler(value = BindException.class)
//    public ResultVO handleValidException(BindException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        String message = null;
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = bindingResult.getFieldError();
//            if (fieldError != null) {
//                message = fieldError.getField()+fieldError.getDefaultMessage();
//            }
//        }
//        return ResultVO.fail(message);
//    }
//}
