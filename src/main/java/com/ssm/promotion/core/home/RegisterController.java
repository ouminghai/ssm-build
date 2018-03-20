package com.ssm.promotion.core.home;

import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.entity.Register;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.RegisterService;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @Resource
    private UserService userService;

    private static final Logger log = Logger.getLogger(RegisterController.class);


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

            //判断用户是否存在
            User registerUser = new User();
            registerUser.setUserName(register.getUserName());
            User s = userService.getOneUser(registerUser);
            if(s !=null){
                return ResultGenerator.genFailResult("用户已存在,请重试");
            }

            String MD5pwd = MD5Util.MD5Encode(register.getPassword(), "UTF-8");
            register.setPassword(MD5pwd);

        } catch (Exception e) {
            register.setPassword("");
        }
        int resultTotal = registerService.register(register);
        log.info("request: register/register , user: " + register.toString());

        if (resultTotal < 0 ) {
            return ResultGenerator.genFailResult("注册失败");
        } else {
            register.setPassword("PASSWORD");
            Map data = new HashMap();
            data.put("currentUser", register);
            return ResultGenerator.genSuccessResult(data);
        }

    }


}
