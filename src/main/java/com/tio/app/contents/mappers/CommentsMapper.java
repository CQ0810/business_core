package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.entities.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* Created by Mybatis Generator 2018/11/14
*/
@Mapper
@Component
public interface CommentsMapper extends BaseMapper<Comments> {
    int deleteByPrimaryKey(Integer id);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);
}