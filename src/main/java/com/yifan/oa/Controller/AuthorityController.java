package com.yifan.oa.Controller;


import com.yifan.oa.Base.BaseController;
import com.yifan.oa.Entity.Authority;
import com.yifan.oa.Entity.User;
import com.yifan.oa.Mapper.AuthorityMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityController extends BaseController {

    @Autowired
    AuthorityMapper AuthorityMapper;

    @GetMapping("/getList")
    public String getAuthorityList() {
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }


        List<Authority> list = this.AuthorityMapper.selectList(null);
        this.result.put("list", list);
        return this.toPageJson(SUCCESS_CODE);
    }


    @PostMapping("/save")
    public String setAuthority(Authority entity) {
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }
        if(null==user.getAuthorityid()){
            return this.toPageJson(ERROR_CODE,"你没有权限");
        }
        if (null == entity.getId()) {
            this.AuthorityMapper.insert(entity);
        } else {
            this.AuthorityMapper.updateById(entity);
        }
        return this.toPageJson(SUCCESS_CODE);
    }
}
