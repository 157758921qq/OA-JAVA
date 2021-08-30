package com.marcus.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marcus.oa.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
public interface IMenuService extends IService<Menu> {




    /**
     * 根据用户id查询菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();




    /**
     * 根据角色获取菜单列表
     */
    List<Menu> getMenusWithRole();
}
