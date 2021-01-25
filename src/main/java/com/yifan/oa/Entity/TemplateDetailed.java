package com.yifan.oa.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "template")
@Entity
@Data
public class TemplateDetailed {

    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer templateid;
    String detailedname;

}
