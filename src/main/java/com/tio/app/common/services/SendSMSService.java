package com.tio.app.common.services;

import java.util.Map;

public interface SendSMSService {
    Map<String, Object> send(String phone);

    int verification(String phone, String code);
}
