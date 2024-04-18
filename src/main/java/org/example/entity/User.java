package org.example.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @NotNull
    @TableId(type = IdType.AUTO)
    private Integer id;//主键ID
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;//用户名
    @JsonIgnore//让springmvc把当前对象转换成json字符串时，忽略password，最终的json字符串中就没有password这个属性了
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //如果说某个校验项没有指定分组，默认属于Default分组
    //分组之间可以继承，A extends B 那么A拥有B中所有的校验项
    public interface Add extends Default {}
    public interface Update extends Default{}
}
