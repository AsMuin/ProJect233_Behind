package org.example.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.example.anno.State;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
@Data
public class Article {
    @NotNull(groups = Update.class)
    @TableId(type = IdType.AUTO)
    private Integer id;//主键ID
    @NotEmpty(groups = Add.class)
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;//文章标题
    @NotEmpty(groups = Add.class)
    private String content;//文章内容
    @NotEmpty(groups = Add.class)
    @URL
    private String coverImg;//封面图像
    @State
    private String state;//发布状态 已发布|草稿
    @NotNull(groups = Add.class)
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //如果说某个校验项没有指定分组，默认属于Default分组
    //分组之间可以继承，A extends B 那么A拥有B中所有的校验项
    public interface Add extends Default {}
    public interface Update extends Default{}
}
