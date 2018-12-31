package com.tio.app.user.services;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.user.entityes.UUser;

/**
 * @author likui
 * @create 2018-12-26
 **/
public interface UUserService {

    /**
     * 添加用户表
     * @return
     */
    ResultUtil saveUser(String phone, UUser user);

    /**
     * 修改用户
     * @param uUser
     * @return
     */
    ResultUtil updateUser(UUser uUser);


    /**
     * 是否启用
     * @return
     */
    ResultUtil isNo(Integer uid);
}
