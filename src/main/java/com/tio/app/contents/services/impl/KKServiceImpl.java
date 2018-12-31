package com.tio.app.contents.services.impl;

import com.tio.app.contents.services.TestService;
import org.springframework.stereotype.Service;

@Service
public class KKServiceImpl implements TestService {
    @Override
    public String test() {
        return "aaaaa-mmmm-kkkk-lll";
    }
}
