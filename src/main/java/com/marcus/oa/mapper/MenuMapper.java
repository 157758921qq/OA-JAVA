package com.marcus.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marcus.oa.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id， 获取对应的菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);


    /**
     * 根据用户角色， 获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();
}
