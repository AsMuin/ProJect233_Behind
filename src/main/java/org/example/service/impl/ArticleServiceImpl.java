package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.entity.Article;
import org.example.entity.PageBean;
import org.example.mapper.ArticleMapper;
import org.example.service.ArticleService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNumber, Integer pageSize, String categoryId, String state, Integer userId){
        //1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNumber, pageSize);

        //3.调用mapper
        List<Article> ArticleList = articleMapper.list(categoryId,state,userId);
        //Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) ArticleList;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;

    }

    @Override
    public void update(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
         articleMapper.update(article, userId);
    }

    @Override
    public void delete(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        articleMapper.delete(id, userId);
    }
}
