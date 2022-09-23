package com.krito3.base.scaffold.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krito3.base.scaffold.common.result.ResultPageVO;
import com.krito3.base.scaffold.common.result.ResultVO;
import com.krito3.base.scaffold.module.entity.UmsResource;
import com.krito3.base.scaffold.service.UmsResourceService;
import com.krito3.base.scaffold.security.component.DynamicSecurityMetadataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源管理Controller
 * Created by macro on 2020/2/4.
 */
@Controller
@Api(tags = "UmsResourceController")
@Tag(name = "UmsResourceController",description = "后台资源管理")
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO create(@RequestBody UmsResource umsResource) {
        boolean success = resourceService.create(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("");
        }
    }

    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO update(@PathVariable Long id,
                           @RequestBody UmsResource umsResource) {
        boolean success = resourceService.update(id, umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("");
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = resourceService.getById(id);
        return ResultVO.data(umsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO delete(@PathVariable Long id) {
        boolean success = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("");
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<ResultPageVO<UmsResource>> list(@RequestParam(required = false) Long categoryId,
                                                    @RequestParam(required = false) String nameKeyword,
                                                    @RequestParam(required = false) String urlKeyword,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsResource> resourceList = resourceService.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return ResultVO.data(ResultPageVO.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<List<UmsResource>> listAll() {
        List<UmsResource> resourceList = resourceService.list();
        return ResultVO.data(resourceList);
    }
}
