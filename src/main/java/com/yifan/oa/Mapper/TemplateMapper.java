package com.yifan.oa.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.oa.Entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;


@Controller
@Mapper
public interface TemplateMapper extends BaseMapper<Template> {



}
