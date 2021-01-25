package com.yifan.oa.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name = "charts_detailed")
@Entity
@Data
public class ChartsDetailed {

    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    Integer chartsid;
    Integer authorityid;
    String price;



}
