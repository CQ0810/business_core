package com.tio.app.sys.entities;

import com.tio.app.common.vo.request.PageVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SRoles extends PageVO implements Serializable {

    private Integer id;

    //角色标识名字
    private String name;

    //角色显示名字
    private String showName;

    private String guardName;

    //角色的类型，1：管理员角色，2：其他
    private Integer type;

    //状态，1：可用，0：冻结
    private Integer status;

    //备注
    private String remark;

    private Integer createdAt;

    private Integer updatedAt;
}
