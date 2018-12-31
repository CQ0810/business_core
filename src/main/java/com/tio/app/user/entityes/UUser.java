package com.tio.app.user.entityes;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author likui
 * @create 2018-12-26
 **/

@Data
public class UUser{

    private Integer id;

//  部门
    private String department;
//    手机号码
    private String phone;
//    公司名字
    private String companyName;
//    公司别名
    private String companyAlias;
//    联系人
    private String contactName;
//    联系电话
    private String contactPhone;
//    省
    private Integer provinceId;
//    市
    private Integer cityId;
//    区
    private Integer areaId;
//    联系地址
    private String contactAddress;
//    查询设置
    private String queryProperty;
//    身份证
    private String identityCard;
//    身份证有效期（0：长期有效）
    private Integer validityIdCard;
//    委托书有效期（0：长期有效）
    private Integer validityProxy;
//    创建时间
    private Integer createdAt;
//    更新时间
    private Integer updatedAt;

    private String userName;

    private String pwd;

    private  String userNickName;

    private Integer isNo;

    @TableField(exist = false)
    private String code;
}
