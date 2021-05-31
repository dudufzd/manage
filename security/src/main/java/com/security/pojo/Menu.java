package com.security.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author zs
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单ID
     */
    private Integer parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单类型10:目录;20:菜单;30:按钮
     */
    private Integer menuType;

    /**
     * 菜单状态10:显示;20:隐藏
     */
    private Integer visible;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

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
