package com.yifan.oa.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yifan.oa.Base.BaseController;
import com.yifan.oa.Entity.Template;
import com.yifan.oa.Entity.TemplateDetailed;
import com.yifan.oa.Entity.User;
import com.yifan.oa.Mapper.TemplateDetailedMapper;
import com.yifan.oa.Mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {

    @Autowired
    TemplateMapper TemplateMapper;

    @Autowired
    TemplateDetailedMapper TemplateDetailedMapper;

    @GetMapping("/getList")
    public String getTemplateList() {
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }
        List<Template> list = this.TemplateMapper.selectList(null);
//        String in = list.stream().map(l -> l.getId().toString()).collect(Collectors.joining(","));
//        QueryWrapper<TemplateDetailed> TemplateDetailedwhere = new QueryWrapper<TemplateDetailed>();
//        TemplateDetailedwhere.in("templateid", 1,2);
//        List<TemplateDetailed> TemplateDetailedlist = TemplateDetailedMapper.selectList(TemplateDetailedwhere);
//        if (list.isEmpty()) {
//            for (int i = 0; i < list.size(); i++) {
//                Integer Templateid = list.get(i).getId();
//                List<TemplateDetailed> llist = TemplateDetailedlist.stream().filter(t -> t.getTemplateid() == Templateid).collect(Collectors.toList());
//                list.get(i).setTemplateDetailedList(llist);
//            }
//        }
        this.result.put("list", list);
        return this.toPageJson(SUCCESS_CODE);
    }

    @GetMapping("/get")
    public String getTemplate(Integer id) {
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }

        Template  template=this.TemplateMapper.selectById(id);
        if(null!=template){
            QueryWrapper<TemplateDetailed> qw=new QueryWrapper<TemplateDetailed>();
            qw.eq("templateid",template.getId());
            List<TemplateDetailed> TemplateDetailedlist=this.TemplateDetailedMapper.selectList(qw);
            template.setTemplateDetailedList(TemplateDetailedlist);
        }

        this.result.put("template", template);
        return this.toPageJson(SUCCESS_CODE);
    }


    @PostMapping("/save")
    public String setUser(Template entity, String josnarray) {
        User user = this.getUser();
        if(null==user){
            return this.toPageJson(ERROR_CODE,"登陆超时，请重新登陆");
        }
        if(null==user.getAuthorityid()){
            return this.toPageJson(ERROR_CODE,"你没有权限");
        }
        entity.setAuthorityid(user.getAuthorityid());
        if (null == entity.getId()) {
            this.TemplateMapper.insert(entity);
        }else {
            this.TemplateMapper.updateById(entity);
            this.TemplateDetailedMapper.delete(new QueryWrapper<TemplateDetailed>().eq("templateid",entity.getId()));
        }
        JSONArray Array = JSON.parseArray(josnarray);
        for (int i = 0; i < Array.size(); i++) {
            JSONObject object = (JSONObject) Array.get(i);
            TemplateDetailed TemplateDetailed = JSON.toJavaObject(object, TemplateDetailed.class);
            TemplateDetailed.setTemplateid(entity.getId());
            TemplateDetailedMapper.insert(TemplateDetailed);
        }
        return this.toPageJson(SUCCESS_CODE);
    }
}
