package com.krito3.base.scaffold.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krito3.base.scaffold.common.model.PageVO;
import com.krito3.base.scaffold.common.result.ResultVO;
import com.krito3.base.scaffold.module.dto.UmsMenuNode;
import com.krito3.base.scaffold.module.entity.UmsMenu;
import com.krito3.base.scaffold.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台菜单管理Controller
 * Created by macro on 2020/2/4.
 */
@Controller
@Api(tags = "UmsMenuController")
@Tag(name = "UmsMenuController",description = "后台菜单管理")
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation("添加后台菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO create(@RequestBody UmsMenu umsMenu) {
        boolean success = menuService.create(umsMenu);
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("未知错误！");
        }
    }

    @ApiOperation("修改后台菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO update(@PathVariable Long id,
                           @RequestBody UmsMenu umsMenu) {
        boolean success = menuService.update(id, umsMenu);
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("未知错误！");
        }
    }

    @ApiOperation("根据ID获取菜单详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<UmsMenu> getItem(@PathVariable Long id) {
        UmsMenu umsMenu = menuService.getById(id);
        return ResultVO.data(umsMenu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO delete(@PathVariable Long id) {
        boolean success = menuService.removeById(id);
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("未知错误！");
        }
    }

    @ApiOperation("分页查询后台菜单")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<PageVO<UmsMenu>> list(@PathVariable Long parentId,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return ResultVO.data(PageVO.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> list = menuService.treeList();
        return ResultVO.data(list);
    }

    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        boolean success = menuService.updateHidden(id, hidden);
        if (success) {
            return ResultVO.data(null);
        } else {
            return ResultVO.fail("未知错误！");
        }
    }
}
