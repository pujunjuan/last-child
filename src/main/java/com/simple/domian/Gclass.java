package com.simple.domian;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Data
@Alias("gclass")
@TableName("class")
public class Gclass implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;//班级ID
    private String name;//班级名
    @TableField("t_id")
    private long tid;//班主任
    private String info;//备注
    @TableLogic
    private Integer deleted;
    @TableField(exist = false)
    private Teacher teacher;

    @TableField(exist = false)
    private List<Student> student;

}
