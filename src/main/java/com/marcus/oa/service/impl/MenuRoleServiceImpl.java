package com.marcus.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marcus.oa.mapper.MenuRoleMapper;
import com.marcus.oa.pojo.MenuRole;
import com.marcus.oa.pojo.RespBean;
import com.marcus.oa.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;


    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if(null == mids || 0 == mids.length){
            return RespBean.success("更新成功");
        }

        //批量更新
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if(result == mids.length){
            return RespBean.success("批量更新成功！");
        }
        return RespBean.error("批量更新失败！");
    }
}
