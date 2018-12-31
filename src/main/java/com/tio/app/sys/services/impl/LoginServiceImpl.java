package com.tio.app.sys.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.oauth.TokenManager;
import com.tio.app.common.utils.CommonUtil;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.mappers.SAdminMapper;
import com.tio.app.sys.services.LoginService;
import com.tio.app.sys.vo.request.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl extends ServiceImpl<SAdminMapper, SAdmin> implements LoginService {
    
    @Override
    public ResultUtil loginUser(LoginVO loginVO) {
        //查询登录用户
        SAdmin admin = getOne(new QueryWrapper<SAdmin>().eq("nick_name", loginVO.getUserName()).ne("status", 2));
        if (admin == null) {
            return ResultUtil.error("用户不存在");
        }
        if (admin.getStatus() == 0) {
            return ResultUtil.error("账号被冻结");
        }
        String md5 = CommonUtil.MD5(loginVO.getPwd());
        if (!admin.getPassword().equals(md5)) {
            return ResultUtil.error("密码不匹配");
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user_id", admin.getId());
        map.put("token", TokenManager.generateToken(userInfo));
        return ResultUtil.ok().put("data", map);
    }
}
