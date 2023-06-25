package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("behavior")
@TableName("daily_perform")
public class Behavior implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("t_id")
    private Long tid;//学生id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
    private String image;//图片
    private String context;//今日表现内容
    @TableField(exist = false)
    private Student student;
}
