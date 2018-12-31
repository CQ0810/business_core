package com.tio.app.contents.services.impl;

import com.tio.app.contents.services.TestService;
import com.tio.app.sys.mappers.SAdminHasPermissionsMapper;
import com.tio.app.sys.mappers.SAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TestServiceImpl implements TestService {
    @Autowired
    private SAdminMapper sAdminMapper;

    @Override
    public String test() {
        return "hello XXXXX LLLLL";
    }
}
