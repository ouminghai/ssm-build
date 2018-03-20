package com.ssm.promotion.core.home;


import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    private static final Logger log =  LoggerFactory.getLogger(LoginController.class);// 日志文件

    /**
     * 用户系统登陆逻辑
     * @param user
     * @return
     */
    @RequestMapping(value = "/userlogin" , method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user , @RequestParam(value = "verifty" ,required = false) String verify ,
                        HttpSession session) {
        try {
            String MD5pwd = MD5Util.MD5Encode(user.getPassword() , "UTF-8");
            user.setPassword(MD5pwd);
        } catch (Exception e) {
            user.setPassword("");
        }
        User resultUser = userService.getOneUser(user);

        if( resultUser == null ){
            return ResultGenerator.genFailResult("帐号不存在");
        }else{

            if(user.getPassword().equals(resultUser.getPassword())){
                //登陆成功
                log.info("request: user/login , user: " + user.toString());
                //清空密码
                resultUser.setPassword("PASSWORD");
                //写入session
                session.setAttribute("currentUser",resultUser);

                Map data = new HashMap();
                data.put("currentUser", resultUser);
                return ResultGenerator.genSuccessResult(data);
            }else{
                //登陆失败
                return ResultGenerator.genFailResult("密码不正确");
            }

        }

    }

    /**
     * 用户系统登陆逻辑
     * @param session
     * @return
     */
    public Result logout(HttpSession session){
        session.removeAttribute("currentUser");
        return ResultGenerator.genSuccessResult();
    }


}
