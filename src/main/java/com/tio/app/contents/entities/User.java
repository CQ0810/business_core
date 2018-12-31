package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class User extends Model<User> {
    /* */
    private Integer id;

    /* */@Length(min = 6, max = 20)/**/
    private String username;

    /* */
    @NotNull
    @Length(min = 5, max = 16)
    /**/
    private String password;

    /* */
    private Integer createdAt;

    /* */
    private Integer updatedAt;

    /* */
    private String discr;

    @TableField(exist = false)
    private List<Posts> posts;

    @TableField(exist = false)
    private List<Orders> orders;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}