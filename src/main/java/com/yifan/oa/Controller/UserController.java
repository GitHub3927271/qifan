package com.yifan.oa.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yifan.oa.Base.BaseController;
import com.yifan.oa.Entity.Authority;
import com.yifan.oa.Entity.User;
import com.yifan.oa.Mapper.AuthorityMapper;
import com.yifan.oa.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserMapper usermapper;

    @Autowired
    AuthorityMapper AuthorityMapper;

    @ResponseBody
    @GetMapping("/getList")
    public String getUserList(){
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }

        List<User> userlist=this.usermapper.selectList(null);
        QueryWrapper<Authority> qw=new QueryWrapper<Authority>();
        qw.inSql("id","select authorityid from  user");
        List<Authority> Authoritylist= AuthorityMapper.selectList(qw);
        for (int i = 0; i <userlist.size() ; i++) {
            User u=userlist.get(i);
            Authority ty= Authoritylist.stream().filter(a->null!=u.getAuthorityid()&& a.getId().intValue()==u.getAuthorityid().intValue()).findFirst().orElse(null);
            if(null!=ty){
                userlist.get(i).setAuthorityname(ty.getAuthorityname());
            }
        }
        this.result.put("list",userlist);
        return this.toPageJson(SUCCESS_CODE);
    }

    @ResponseBody
    @PostMapping("/save")
    public String setUser( User entity){
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }
        if(null==entity.getId()){
            entity.setCrtime(new Date());
            entity.setStatus(1);
            this.usermapper.insert(entity);
        }else {
            this.usermapper.updateById(entity);
        }
        return this.toPageJson(SUCCESS_CODE);
    }
}
