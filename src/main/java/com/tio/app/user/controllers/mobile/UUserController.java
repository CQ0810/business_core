package com.tio.app.user.controllers.mobile;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.user.entityes.UUser;
import com.tio.app.user.services.UUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author likui
 * @create 2018-12-26
 * 用户表
 **/
@RequestMapping(value = "/user")
@RestController
@Slf4j
public class UUserController {

    @Autowired
    private UUserService uUserService;

    /**
     * 禁用、启用用户
     * @param uid
     * @return
     */
    @GetMapping("/is-no")
    public ResultUtil isNo(@RequestParam Integer uid) {
        return uUserService.isNo(uid);
    }

    /**
     * 修改用户表
     * @param user
     * @return
     */
    @PostMapping("/update-user")
    public ResultUtil updateUser(@RequestBody UUser user) {
        return uUserService.updateUser(user);
    }

}
