package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@Alias("cookbook")
@TableName("cook_book")
public class CookBook implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;//食谱Id
    private String title;//食谱名字
    private String image;//图片
    private String type;//类型
    private String list;//清单
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;//日期
    private String canalyze;//营养分析
    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;
}
