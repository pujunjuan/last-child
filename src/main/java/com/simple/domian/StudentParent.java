package com.simple.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("studentparent")
@TableName("student_parent")
public class StudentParent  implements Serializable {
    /** 学生ID */
    @TableField("student_id")
    private Long studentId;
    /** 家长ID */
    @TableField("parent_id")
    private Long parentId;

    private String relation;

    @TableField(exist = false)
    private Student student;

    @TableField(exist = false)
    private Parent parent;
}
