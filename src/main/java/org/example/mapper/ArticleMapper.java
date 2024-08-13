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
    @Update("UPDATE article a " +
            "JOIN user u ON u.id = #{userId} " +
            "SET a.title = #{article.title}, a.content = #{article.content}, " +
            "a.cover_img = #{article.coverImg}, a.state = #{article.state}, " +
            "a.category_id = #{article.categoryId}, a.update_time = NOW() " +
            "WHERE (a.create_user = #{userId} OR u.user_Role = TRUE) " +
            "AND a.id = #{article.id}")
    void update(@Param("article") Article article, @Param("userId") Integer userId);

    //删除文章
    @Delete("DELETE article FROM article JOIN user ON user.id = #{userId} WHERE article.id=#{id} " +
            "AND (article.create_user=#{userId} OR user.user_role = TRUE)")
    void delete(@Param("id") Integer id, @Param("userId") Integer userId);
}
