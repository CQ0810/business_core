package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.vo.request.LoginVO;

public interface LoginService extends IService<SAdmin> {
    ResultUtil loginUser(LoginVO loginVO) throws Exception;
}
