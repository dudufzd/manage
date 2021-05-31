package com.security.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zs
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别10:男;11:女;12:其他
     */
    private Integer sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 状态10:正常;11:锁定;12:注销
     */
    private Integer status;

    /**
     * 删除标识0:未删除;1:已删除
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

}
