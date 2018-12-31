package com.tio.app.sys.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SAdminExtra {
    @TableId
    private int sAdminId;
    private String department;
    private String phone;
    private String companyName;
    private String companyAlias;
    private String contactName;
    private String contactPhone;
    private int provinceId;
    private int cityId;
    private int areaId;
    private String contactAddress;
    private String queryProperty;
    private String identityCard;
    private int validityIdCard;
    private int validityProxy;
    private int isNo;
    private int imgCard;
    private int imgProxy;
    private int createdAt;
    private int updatedAt;
}
