package com.yifan.oa.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yifan.oa.Base.BaseController;
import com.yifan.oa.Entity.*;
import com.yifan.oa.Mapper.ChartsDetailedMapper;
import com.yifan.oa.Mapper.ChartsMapper;

import com.yifan.oa.Mapper.TemplateDetailedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/charts")
public class ChartsController extends BaseController {

    @Autowired
    ChartsMapper ChartsMapper;

    @Autowired
    ChartsDetailedMapper ChartsDetailedMapper;

    @Autowired
    TemplateDetailedMapper TemplateDetailedMapper;

    @GetMapping("/getList")
    public String getChartsList() {
        User user = this.getUser();
        if (null == user) {
            return this.toPageJson(ERROR_CODE, "登陆超时，请重新登陆");
        }

        QueryWrapper<Charts> queryWrapper = new QueryWrapper<Charts>();
        queryWrapper.ne("status", "-1");
        List<Charts> list = this.ChartsMapper.selectList(queryWrapper);
        this.result.put("list", list);
        return this.toPageJson(SUCCESS_CODE);
    }

    @GetMapping("/get")
    public String getCharts(Integer id) {
        User user = this.getUser();
        if (null == user) {
            return this.toPageJson(ERROR_CODE, "登陆超时，请重新登陆");
        }
        Charts charts = this.ChartsMapper.selectById(id);
        if (null != charts) {
            QueryWrapper<ChartsDetailed> qw = new QueryWrapper<ChartsDetailed>();
            qw.eq("chartsid", charts.getId());
            List<ChartsDetailed> TemplateDetailedlist = this.ChartsDetailedMapper.selectList(qw);
            charts.setChartsDetailedList(TemplateDetailedlist);
        }
        this.result.put("charts", charts);
        return this.toPageJson(SUCCESS_CODE);
    }

    @GetMapping("/init")
    public String initCharts() {
        User user = this.getUser();
        if (null == user) {
            return this.toPageJson(ERROR_CODE, "登陆超时，请重新登陆");
        } if(null==user.getAuthorityid()){
            return this.toPageJson(ERROR_CODE,"你没有权限");
        }
        QueryWrapper<TemplateDetailed> qw = new QueryWrapper<TemplateDetailed>();
        qw.inSql("templateid", "SELECT id from template where authorityid="+user.getAuthorityid());
        List<TemplateDetailed> TemplateDetailedlist=TemplateDetailedMapper.selectList(qw);
        List<ChartsDetailed> ChartsDetailedlist= new ArrayList<ChartsDetailed>();
        for (int i = 0; i <TemplateDetailedlist.size() ; i++) {
            ChartsDetailed cd= new ChartsDetailed();
            cd.setName(TemplateDetailedlist.get(i).getDetailedname());
            ChartsDetailedlist.add(cd);
        }
        Charts Charts=new Charts();
        Charts.setChartsDetailedList(ChartsDetailedlist);
        this.result.put("Charts",Charts);
        return this.toPageJson(SUCCESS_CODE);
    }


    @PostMapping("/save")
    public String setCharts(Charts entity, String josnarray) {
        User user = this.getUser();
        if (null == user) {
            return this.toPageJson(ERROR_CODE, "登陆超时，请重新登陆");
        } if(null==user.getAuthorityid()){
            return this.toPageJson(ERROR_CODE,"你没有权限");
        }
        JSONArray Array = JSON.parseArray(josnarray);
        if (null == entity.getId()) {
            entity.setCrtime(new Date());
            entity.setStatus(1);
            entity.setCruserid(user.getId());
            entity.setCrusername(user.getUserfullname());
            this.ChartsMapper.insert(entity);

        } else {
            entity.setUptime(new Date());
            entity.setUpuserid(user.getId());
            entity.setUpusername(user.getUserfullname());
            this.ChartsMapper.updateById(entity);
            QueryWrapper<ChartsDetailed> qw = new QueryWrapper<ChartsDetailed>();
            qw.eq("chartsid", entity.getId());
            this.ChartsDetailedMapper.delete(qw);
        }
        for (int i = 0; i < Array.size(); i++) {
            JSONObject object = (JSONObject) Array.get(i);
            ChartsDetailed chartsDetailed = JSON.toJavaObject(object, ChartsDetailed.class);
            chartsDetailed.setChartsid(entity.getId());
            chartsDetailed.setAuthorityid(user.getAuthorityid());
            ChartsDetailedMapper.insert(chartsDetailed);

        }
        return this.toPageJson(SUCCESS_CODE);
    }
}
