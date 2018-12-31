package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.builders.PostsSqlBuilder;
import com.tio.app.contents.entities.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator 2018/11/14
 */
@Mapper
@Component
public interface PostsMapper extends BaseMapper<Posts> {
    int deleteByPrimaryKey(Integer id);

    int insert(Posts record);

    int insertSelective(Posts record);

    Posts selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Posts record);

    int updateByPrimaryKey(Posts record);

    @SelectProvider(type = PostsSqlBuilder.class, method = "getPostsByUserId")
    List<Posts> getListPostsByUserId(Integer userId);
}