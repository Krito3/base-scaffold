package com.krito3.base.scaffold.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.krito3.base.scaffold.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @classDesc: 功能描述: 控制层面向切面打日志
 * @createTime 2018年6月29日 上午11:28:07
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspectController {

    @Autowired
    private RedisService redisService;

    //接口消费时间
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    // 申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * com.krito3.base.scaffold.controller.*.*(..))")
    private void controllerAspect() {
    }

    // 请求method前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("===============请求内容 开始==============");
        try {
            // 打印请求内容
            log.info("请求地址:" + request.getRequestURL().toString());
            log.info("请求方式:" + request.getMethod());
            log.info("请求类方法:" + joinPoint.getSignature());
            log.info("请求类方法参数:" + Arrays.toString((joinPoint.getArgs())));
        } catch (Exception e) {
            log.error("###LogAspectController.class methodBefore() ### ERROR:", e);
        }
        log.info("===============请求内容 结束===============");
    }

    // 在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturing(Object o) {
        // 处理完请求，返回内容
        log.info("===============返回内容 开始===============");
        try {
            log.info("SPEND TIME :{},Response内容:{}", (System.currentTimeMillis() - startTime.get()), JSON.toJSONString(o));
            //log.info("请求服务的ip:{}",this.auditorProvider.getReqIp());//请求ip
            //log.info("SPEND TIME :{}", (System.currentTimeMillis() - startTime.get()));//请求接口的耗时，单位是毫秒

        } catch (Exception e) {
            log.error("###LogAspectController.class methodAfterReturing() ### ERROR:{}", e, e);
        }
        log.info("===============返回内容 结束===============");
    }
}
