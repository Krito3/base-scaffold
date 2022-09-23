package com.krito3.base.scaffold.common.exception;

import com.krito3.base.scaffold.common.result.ResultVO;
import com.krito3.base.scaffold.common.result.ResultCode;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

/**
 * @author kno.ci
 * @description: 全局异常
 * @date 2022/2/12 下午3:46
 */
@ControllerAdvice
public class GlobalExConfig {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExConfig.class);

    /**
     * 业务异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleServiceException(BusinessException e) {
        logger.warn("业务异常:{}", e.getMessage());
        return ResultVO.fail(e.getMessage());
    }
    /**
     * 接口空指针异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleNullPointerException(NullPointerException e) {
        logger.error("空指针异常:", e);
        return ResultVO.fail("数据空值异常");
    }

    /**
     * 接口参数异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("请求参数类型异常:", e);
        return ResultVO.fail("传入参数类型错误");
    }


    /**
     * 接口参数异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("HTTP数据读取异常:", e);
        return ResultVO.fail("HTTP数据读取异常");
    }


    /**
     * 接口参数异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("请求参数异常:{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResultVO.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    /**
     * 算术异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleArithmeticException(ArithmeticException e) {
        logger.error("算术异常:", e);
        return ResultVO.fail("算术异常");
    }

    /**
     * 数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleDatabaseException(DataIntegrityViolationException e) {
        logger.error("数据库异常:", e);
        return ResultVO.fail("数据库异常");
    }

    /**
     * Mysql异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleMySQLSyntaxErrorException(SQLException e) {
        logger.error("sql语句异常:", e);
        return ResultVO.fail("sql语句异常");
    }

    /**
     * 服务异常
     *
     * @param e
     * @return JSON数据
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleZSYServiceException(Exception e) {
        logger.error("系统异常:", e);
        return ResultVO.fail("系统异常请稍后再试");
    }

    @ExceptionHandler
    public @ResponseBody
    ResultVO handleZSYServiceException(MaxUploadSizeExceededException e) {
        logger.error("文件过大:", e);
        return ResultVO.fail("文件大小不能超过10M");
    }

    @ExceptionHandler
    public @ResponseBody
    ResultVO handleZSYServiceException(IllegalStateException e) {
        logger.error("图片过大:", e);
        return ResultVO.fail("图片大小不能超过10M");
    }

    @ExceptionHandler
    public @ResponseBody
    ResultVO handleZSYServiceException(SizeLimitExceededException e) {
        logger.error("图片过大:", e);
        return ResultVO.fail("图片大小不能超过10M");
    }

    /**
     * 请求方法异常  get post。。
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public @ResponseBody
    ResultVO handleException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法:{}", e.getMessage());
        return ResultVO.fail(ResultCode.METHOD_NOT_SUPPORTED, e.getMessage());
    }

}
