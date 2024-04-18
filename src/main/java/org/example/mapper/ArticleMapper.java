package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)"
            + " values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(Article article);

    List<Article> list(String categoryId, String state, Integer userId);

    //修改文章信息
    @Update("update article set title=#{article.title}, content=#{article.content}, " +
            "cover_img=#{article.coverImg}, state=#{article.state}, category_id=#{article.categoryId}, " +
            "update_time=now() where create_user=#{userId} and id=#{article.id}")
    void update(@Param("article") Article article, @Param("userId") Integer userId);

    //删除文章
    @Delete("delete from article where id=#{id} and create_user=#{userId}")
    void delete(@Param("id") Integer id, @Param("userId") Integer userId);
}