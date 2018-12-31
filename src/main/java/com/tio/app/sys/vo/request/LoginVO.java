package com.tio.app.sys.vo.request;

import lombok.Data;

@Data
public class LoginVO {

    private String uuid;

    private String userName;

    private String code;

    private String pwd;
}
