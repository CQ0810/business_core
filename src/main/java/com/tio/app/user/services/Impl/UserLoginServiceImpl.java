package com.tio.app.user.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tio.app.common.oauth.TokenManager;
import com.tio.app.common.utils.CommonUtil;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.vo.request.LoginVO;
import com.tio.app.user.entityes.UUser;
import com.tio.app.user.mappers.UUserMapper;
import com.tio.app.user.services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likui
 * @create 2018-12-26
 **/
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UUserMapper uUserMapper;

    @Override
    public ResultUtil loginUserName(LoginVO loginVO) {
        UUser user=uUserMapper.selectOne(new QueryWrapper<UUser>().eq("user_name",loginVO.getUserName()));
        if(user==null){
          return  ResultUtil.error("账号错误");
        }
        if(!user.getPwd().equals(CommonUtil.MD5(loginVO.getPwd()))){
            return  ResultUtil.error("密码错误");
        }
        if(user.getIsNo()==0){
            return  ResultUtil.error("对不起,该账号已被冻结");
        }
        return ResultUtil.ok().putData(toToken(user.getId()));
    }

    @Override
    public ResultUtil loginPhone(LoginVO loginVO) {
        UUser user=uUserMapper.selectOne(new QueryWrapper<UUser>().eq("contact_phone",loginVO.getUserName()));
        if(user==null){
            return  ResultUtil.error("账号不存在");
        }
        if(user.getIsNo()==0){
            return  ResultUtil.error("对不起,该账号已被冻结");
        }
        return ResultUtil.ok().putData(toToken(user.getId()));
    }

    private String toToken(Integer uid){
        Map<String, Object> map=new HashMap<>();
        map.put(TokenManager.USERTOKEN,uid);
        String token= TokenManager.generateToken(map);
        return token;
    }
}
