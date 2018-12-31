package com.tio.app.user.services;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.vo.request.LoginVO;

/**
 * @author likui
 * @create 2018-12-26
 **/
public interface UserLoginService {

    /**
     * 用户登录（账号、验证码、密码）
     * @param loginVO
     * @return
     */
    ResultUtil loginUserName (LoginVO loginVO);

    /**
     * 用户登录（手机、验证码）
     * @param loginVO
     * @return
     */
    ResultUtil loginPhone (LoginVO loginVO);

}
