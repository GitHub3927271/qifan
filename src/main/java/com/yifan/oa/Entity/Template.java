package com.yifan.oa.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import java.util.List;

@Table(name = "template")
@Entity
@Data
public class Template {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    String templatename;
    Integer authorityid;

    @TableField(exist = false)
    List<TemplateDetailed> templateDetailedList;
}
