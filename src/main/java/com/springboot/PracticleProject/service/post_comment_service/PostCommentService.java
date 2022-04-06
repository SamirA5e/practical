package com.springboot.PracticleProject.service.post_comment_service;

import com.springboot.PracticleProject.service.comment_service.CommentService;
import com.springboot.PracticleProject.service.post_service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class post_comment_service {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    public void removePostByRemovingComments(String id){

    }
}
