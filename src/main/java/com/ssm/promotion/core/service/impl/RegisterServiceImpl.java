package com.ssm.promotion.core.service.impl;

import com.ssm.promotion.core.dao.RegisterDao;
import com.ssm.promotion.core.entity.Register;
import com.ssm.promotion.core.service.RegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterDao registerDao;

    @Override
    public Register register(Register register) {
        return null;
    }
}
