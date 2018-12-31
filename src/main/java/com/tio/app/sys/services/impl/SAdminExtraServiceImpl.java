package com.tio.app.sys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.sys.entities.SAdminExtra;
import com.tio.app.sys.mappers.SAdminExtraMapper;
import com.tio.app.sys.services.SAdminExtraService;
import org.springframework.stereotype.Service;

@Service
public class SAdminExtraServiceImpl extends ServiceImpl<SAdminExtraMapper, SAdminExtra> implements SAdminExtraService {
}
