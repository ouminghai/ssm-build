package com.ssm.promotion.core.dao;

import com.ssm.promotion.core.entity.Register;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterDao {

    public Register register (Register register);

}
