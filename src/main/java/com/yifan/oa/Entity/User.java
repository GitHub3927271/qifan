package com.yifan.oa.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@Entity
@Data
public class User {

    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    String userfullname;
    Integer authorityid;
    Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date crtime;

    @TableField(exist = false)
    String authorityname;
}
