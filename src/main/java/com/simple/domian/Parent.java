package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("parent")
public class Parent implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private long id;//家长ID
    private String name;//姓名
    private Date birth;//出生日期
    private String telephone;//电话
    private String card;//身份证
    private String address;//地址
    private String sex;//性别
    private String email;//邮箱
    @TableLogic
    private Integer deleted;
}
