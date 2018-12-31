package com.tio.app.contents.controllers.pcweb;

import com.tio.app.contents.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pcweb/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @PostMapping("/add")
    public int add() {
        return postsService.add();
    }
}
