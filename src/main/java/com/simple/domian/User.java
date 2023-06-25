package com.simple.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("User")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;//用户id
    private Long account;//账号
    private String avatar;//用户头像
    private String password;//用户密码
    private Integer type;//用户类别
    private Date createTime;//创建时间
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private Role role;

}
