package com.tio.app.contents.services;

import com.tio.app.contents.entities.Posts;
import com.tio.app.contents.mappers.PostsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    @Autowired
    private PostsMapper postsMapper;

    public int add() {
        Posts posts = new Posts();
        posts.setContent("11111");
        posts.setUserId(1);
        return postsMapper.insert(posts);
    }
}
