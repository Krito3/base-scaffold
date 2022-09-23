package com.krito3.base.scaffold.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.krito3.base.scaffold.enums.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author kno.ci
 * @description:
 * @date 2022/2/13 下午3:33
 */
@Configuration
@Slf4j
public class BaseMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
        setFieldValByName("createBy", 1, metaObject);
        setFieldValByName("updateBy",1, metaObject);
        setFieldValByName("deleted", DeleteEnum.NOT_DELETE.getValue(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(), metaObject);
        setFieldValByName("updateBy", 1, metaObject);
    }
}
