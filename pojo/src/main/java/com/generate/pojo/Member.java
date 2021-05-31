package com.generate.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_member")
@AllArgsConstructor
@NoArgsConstructor
public class Member extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("fileNumber")
    private String filenumber;

    private String name;

    private String sex;

    @TableField("idCard")
    private String idcard;

    @TableField("phoneNumber")
    private String phonenumber;

    @TableField("regTime")
    private LocalDate regtime;

    private String password;

    private String email;

    private LocalDate birthday;

    private String remark;

    @TableLogic
    private Integer deleted;

}
