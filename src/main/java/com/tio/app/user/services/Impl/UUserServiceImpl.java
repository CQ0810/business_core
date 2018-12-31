package com.tio.app.user.services.Impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tio.app.common.utils.*;
import com.tio.app.user.entityes.UUser;
import com.tio.app.user.mappers.UUserMapper;
import com.tio.app.user.services.UUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author likui
 * @create 2018-12-26
 **/
@Service
public class UUserServiceImpl implements UUserService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UUserMapper userMapper;

    @Override
    public ResultUtil saveUser(String phone, UUser user) {
        if (user == null) {
            return ResultUtil.error("参数有误");
        }
        if(StringUtils.isEmpty(user.getUserNickName())){
            return ResultUtil.error("请输入用户昵称");
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResultUtil.error("请输入用户账号");
        }
        if (!user.getUserName().matches("[\\d|\\w]{6,12}")) {
            return ResultUtil.error("用户账号为6-12位字母或数字");
        }
        if (StringUtils.isEmpty(user.getPwd())) {
            return ResultUtil.error("请输入密码");
        }
        if (!user.getPwd().matches("[\\d|\\w]{6,12}")) {
            return ResultUtil.error("用户密码为6-12位字母或数字");
        }
        UUser uer = userMapper.selectOne(new QueryWrapper<UUser>().eq("user_name", user.getUserName()));
        if (uer != null) {
            return ResultUtil.error("该用户名已被占用，请重新输入");
        }
        if (!RegularUtil.RegularCard(user.getIdentityCard())) {
            return ResultUtil.error("身份证格式错误");
        }
        user.setPwd(CommonUtil.MD5(user.getPwd()));
        user.setCreatedAt(TimeUtil.getCurrentUnixTimestamp());
        userMapper.insert(user);
//        清空redis
        String key = RedisKeysUtil.getSMSCodeConfigKey(phone);
        redisUtil.delete(key);
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil updateUser(UUser uUser) {
        if (uUser == null) {
            return ResultUtil.error("参数有误");
        }
        UUser user=userMapper.selectById(uUser.getId());

        if(!StringUtils.isEmpty(uUser.getUserNickName())){
            user.setUserNickName(uUser.getUserNickName());
        }
        if(!StringUtils.isEmpty(uUser.getIdentityCard())){
            if (!RegularUtil.RegularCard(user.getIdentityCard())) {
                return ResultUtil.error("身份证格式错误");
            }
            user.setIdentityCard(uUser.getIdentityCard());
        }
        //  部门
        if(!StringUtils.isEmpty(uUser.getDepartment())){
            user.setDepartment(uUser.getDepartment());
        }
        //    公司别名
        if(!StringUtils.isEmpty(uUser.getCompanyAlias())){
            user.setCompanyAlias(uUser.getCompanyAlias());
        }
        //    联系人
        if(!StringUtils.isEmpty(uUser.getCompanyName())){
            user.setCompanyName(uUser.getCompanyName());
        }
        //    省
        if(uUser.getProvinceId()!=null){
            user.setProvinceId(uUser.getProvinceId());
        }
        //    市
        if(uUser.getCityId()!=null){
            user.setCityId(uUser.getCityId());
        }
        //    区
        if(uUser.getAreaId()!=null){
            user.setAreaId(uUser.getAreaId());
        }
        //    联系人
        if(!StringUtils.isEmpty(uUser.getContactName())){
            user.setContactName(uUser.getContactName());
        }
        if(!StringUtils.isEmpty(uUser.getContactPhone())){
            if(!RegularUtil.RegularPhone(uUser.getContactPhone())){
                return ResultUtil.error("请输入正确的手机格式");
            }else{
                user.setContactPhone(uUser.getContactPhone());
            }
        }
        userMapper.updateById(user);
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil isNo(Integer uid) {
        UUser user=userMapper.selectById(uid);
        if(user.getIsNo()==1){
            user.setIsNo(0);
        }else{
            user.setIsNo(1);
        }
        return ResultUtil.ok();
    }
}
