package com.simple.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Data
@Alias("courseArrange")
@TableName("course_arrange")
public class CourseArrange implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 教师ID */
    @TableField("teacher_id")
    private Long teacherId;

    /** 课程ID */
    @TableField("course_id")
    private Long courseId;

    /** 授课班级id */
    @TableField("class_id")
    private Long classId;

    /** 学期 */
    private int term;

    @TableField(exist = false)
    private Teacher teacher;

    @TableField(exist = false)
    private Gclass gclass;

    @TableField(exist = false)
    private Course course;

}
