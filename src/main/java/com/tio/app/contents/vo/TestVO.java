package com.tio.app.contents.vo;

import com.tio.app.contents.entities.Posts;
import com.tio.app.contents.entities.User;
import lombok.Data;

import javax.validation.Valid;

@Data
public class TestVO {
    @Valid
    private User user;

    @Valid
    private Posts posts;
}
