package com.krito3.base.scaffold.common.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 表基础字段
 * @author dingxiao
 * @date 2022/9/2
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6001494093393676202L;

    @TableId(value = "id", type = IdType.AUTO)//指定自增策略
    private Long id;

    /**
     * 逻辑删除 0：正常 1：已删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
