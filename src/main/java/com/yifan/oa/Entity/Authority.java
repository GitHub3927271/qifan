package com.yifan.oa.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;


import javax.persistence.*;
@Table(name = "authority")
@Entity
@Data
public class Authority {
    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    String authorityname;
    Integer added;
    Integer del;
    Integer alteration;



}
