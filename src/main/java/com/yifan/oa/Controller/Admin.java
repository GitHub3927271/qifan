package com.yifan.oa.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yifan.oa.Base.BaseController;
import com.yifan.oa.Entity.User;
import com.yifan.oa.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Admin extends BaseController {

@Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public String login(String username,String password){
        QueryWrapper<User> qw=new QueryWrapper<User>();
        qw.eq("username",username);
        qw.eq("password",password);
        User user=this.userMapper.selectOne(qw);
        if(null==user){
            return this.toPageJson(ERROR_CODE,"账号或密码错误，请检查");
        }else {
            user.setPassword(null);
            this.result.put("token", JSON.toJSONString(user));
            this.result.put("user",user);
        }

        return this.toPageJson(SUCCESS_CODE);
    }
}
