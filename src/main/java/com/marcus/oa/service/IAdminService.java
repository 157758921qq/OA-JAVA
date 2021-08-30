package com.marcus.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marcus.oa.pojo.Admin;
import com.marcus.oa.pojo.Menu;
import com.marcus.oa.pojo.RespBean;
import com.marcus.oa.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登陆后返回Token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);





    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);



    /**
     * 根据用户ID，查角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
