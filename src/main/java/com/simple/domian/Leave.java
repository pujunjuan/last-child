package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/*
请假表
 */
@Data
@Alias("leave")
@TableName("absence")
public class Leave implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private long id;//请假Id
    private Date createTime;//申请时间
    private Date dealTime;//处理时间
    private String title;//标题（事假，病假）
    private long dealId;//处理者
    private long applyId;//请假者
    private String status;//请假进度
    private Date startTime;//请假开始时间
    private Date endTime;//请假结束时间
    private String reason;//请假原因
    private String remark;//备注
    private int limits;//交给园长处理

    @TableField(exist = false)
    private Student student;

    @TableField(exist = false)
    private Teacher teacher;
    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    @TableField(exist = false)
    public long classId;
}
