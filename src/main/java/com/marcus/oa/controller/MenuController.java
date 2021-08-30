package com.marcus.oa.controller;


import com.marcus.oa.pojo.Menu;
import com.marcus.oa.service.IAdminService;
import com.marcus.oa.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/system/cfg")                    //菜单是放在系统管理里的，它的url：/system/cfg
public class MenuController {



    @Autowired
    private IMenuService menuService;

    /**
     * 根据用户id查询菜单列表
     * @return
     */
    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }
}
