package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检表health
 */
@Data
@Alias("health")
public class Health implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private long id;//Id
    @TableField("s_id")
    private long sid;//学生Id
    private float height;//身高
    private float weight;//体重
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;//日期
    private String blood;//血型
    private String status;//健康情况
    private String line;//成长轨迹
    private String context;//内容

    @TableField(exist =false)
    private Student student;
    @TableField(exist =false)
    private long classId;//学生Id

}
