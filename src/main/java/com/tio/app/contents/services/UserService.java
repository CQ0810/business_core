package com.tio.app.contents.services;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tio.app.contents.vo.TestVO;
import com.tio.app.contents.entities.Posts;
import com.tio.app.contents.entities.User;
import com.tio.app.contents.mappers.PostsMapper;
import com.tio.app.contents.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostsMapper postsMapper;

    @Transactional()
    public int add(TestVO testVO) {
        try {
            User user = testVO.getUser();
            Posts posts = testVO.getPosts();
            user.insert();
            posts.setUserId(user.getId() + 90);
            posts.insert();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return 1;
    }

    public void list() {
        /*Page<User> userPage = new Page<>();
        userPage.setCurrent(2);
        userPage.setSize(2);
       // PageHelper.startPage(1, 2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = userMapper.selectPage(userPage, queryWrapper);
        System.out.println(page.getRecords());*/
        Map<String, Object> data = new HashMap<>();

        Wrapper<User> queryWrapper = new QueryWrapper<>();
       //PageHelper.startPage(1, 1 , true);
        PageHelper.offsetPage(0, 2);
        List<User> list = userMapper.selectAll();
        Page<User> listCountry = (Page<User>)list;
        System.out.println(data);
        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> list = userMapper.selectList(queryWrapper);*/
        /*PageInfo<User> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.toString());*/
        // User t = userMapper.findById(1);
        //System.out.println(t.getPosts());
        //return t;
    }
}
