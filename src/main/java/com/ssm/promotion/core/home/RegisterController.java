package com.ssm.promotion.core.home;

import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.entity.Register;
import com.ssm.promotion.core.service.RegisterService;
import com.ssm.promotion.core.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private RegisterService registerService;
    private static final Logger log = Logger.getLogger(com.ssm.promotion.core.home.RegisterController.class);


    /**
     * 登录
     *
     * @param register
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(Register register){

        try {
            String repassword = register.getRepassword();
            String password = register.getPassword();

            if(!repassword.equals(password)){
                return ResultGenerator.genFailResult("两次输入密码不一致");
            }
            String MD5pwd = MD5Util.MD5Encode(register.getPassword(), "UTF-8");
            register.setPassword(MD5pwd);
        } catch (Exception e) {
            register.setPassword("");
        }
        Register resultRegister = registerService.register(register);
        log.info("request: register/register , user: " + register.toString());

        if (resultRegister == null) {
            return ResultGenerator.genFailResult("请认真核对账号、密码！");
        } else {
            resultRegister.setPassword("PASSWORD");
            Map data = new HashMap();
            data.put("currentUser", resultRegister);
            return ResultGenerator.genSuccessResult(data);
        }

    }


}
