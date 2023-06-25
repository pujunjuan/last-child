package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Alias("Student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private long id;//学生学号
    @TableField("u_id")
    private long usId;//用户Id
    private String name;//姓名
    private Date time;//创建时间
    private Date birth;//出生日期
    private String address;//地址
    private String household;//户口类型
    private String nation;//民族
    private String sex;//性别
    @TableField("class_id")
    private long classId;//班级号
    private String card;//身份证
    @TableLogic
    private Integer deleted;
    @TableField(exist = false)
    public Gclass gclass;
    @TableField(exist = false)
    public long applyId;
    @TableField(exist = false)
    public int num;
    @TableField(exist = false)
    private List<Leave> leaveList;
    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

}
