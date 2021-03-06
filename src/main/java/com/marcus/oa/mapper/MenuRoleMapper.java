package com.marcus.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marcus.oa.pojo.MenuRole;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {


    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
