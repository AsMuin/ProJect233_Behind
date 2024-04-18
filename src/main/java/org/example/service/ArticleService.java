package org.example.service;

import org.example.entity.Article;
import org.example.entity.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNumber, Integer pageSize, String categoryId, String state,Integer userId);

    void update(Article article);

    void delete(Integer id);
}
