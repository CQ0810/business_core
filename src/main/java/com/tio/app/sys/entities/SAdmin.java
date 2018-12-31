package com.tio.app.sys.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tio.app.common.vo.request.PageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SAdmin extends PageVO implements Serializable {
    private Integer id;
    private String wxOpenId;
    private String nickName;
    private String userName;
    private String salt;
    private String password;
    private String telephone;
    private String email;
    private Integer status;
    private String remark;

    @TableField(exist = false)
    private Integer roles;

    @TableField(exist = false)
    private String rolesName;

    @TableField(exist = false)
    private SAdminHasRoles sAdminHasRoles;

    @TableField(exist = false)
    private List<SPermissions> permissions = new ArrayList<>();

    @TableField(exist = false)
    private List<SRoles> rolesk = new ArrayList<>();
}