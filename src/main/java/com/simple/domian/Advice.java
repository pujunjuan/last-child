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

@Data
@Alias("advice")
@TableName("advice")
public class Advice implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;//Id
    @TableField("t_id")
    private Long tid;//反馈者id
    private Date createTime;//创建时间
    private String context;//反馈信息
    private String reply;//回复信息
    private int status;//是否已读状态
    @TableField(exist = false)
    private Student student;
    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;
}
