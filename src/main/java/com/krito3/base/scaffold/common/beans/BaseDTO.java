package com.krito3.base.scaffold.common.beans;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 基础请求入参 VO
 * @author dx
 * @date 2022/3/15 14:41
 */
@Data
@ApiModel("基础请求入参VO")
public class BaseDTO {
    @Min(1)
    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    @NotNull(message = "页码不能为空")
    private Integer pageSize;
}
