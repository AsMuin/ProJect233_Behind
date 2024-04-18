package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增分类信息
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)"+
    "values (#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);
    //查询所有分类信息
    @Select("select * from category ")
    List<Category> list();
    //根据id查询分类信息
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);
    //修改分类信息
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(Category category);

    //删除分类信息
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
