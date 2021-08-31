package com.marcus.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marcus.oa.pojo.MenuRole;
import com.marcus.oa.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
