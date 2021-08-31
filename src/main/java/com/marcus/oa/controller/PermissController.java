package com.marcus.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marcus.oa.pojo.Menu;
import com.marcus.oa.pojo.MenuRole;
import com.marcus.oa.pojo.RespBean;
import com.marcus.oa.pojo.Role;
import com.marcus.oa.service.IMenuRoleService;
import com.marcus.oa.service.IMenuService;
import com.marcus.oa.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;


    @ApiOperation(value ="获取所有角色")
    @GetMapping("/role")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value ="添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_" + role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation(value ="删除角色")
    @DeleteMapping("/role/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        if(roleService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }



    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }


    @ApiOperation(value ="根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        return  menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid)).stream().map(
                MenuRole::getId).collect(Collectors.toList());
    }


    @ApiOperation(value ="更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids){
        return menuRoleService.updateMenuRole(rid, mids);
    }
}
