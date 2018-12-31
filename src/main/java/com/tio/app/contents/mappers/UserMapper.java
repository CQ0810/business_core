package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.builders.UserSqlBuilder;
import com.tio.app.contents.entities.Posts;
import com.tio.app.contents.entities.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @SelectProvider(type = UserSqlBuilder.class, method = "findUserById")
    @Results({
            @Result(property = "posts", column = "id", javaType = List.class, many = @Many(select = "com.tio.app.contents.mappers.PostsMapper.getListPostsByUserId"))
    })
    User findById(Integer userId);
    
    @Select("select * from user")
    List<User> selectAll();
}