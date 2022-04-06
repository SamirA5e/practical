package com.springboot.PracticleProject.controller.comment_cotroller;


import com.springboot.PracticleProject.model.comment_model.CommentRequestModel;
import com.springboot.PracticleProject.model.comment_model.CommentResponseModel;
import com.springboot.PracticleProject.service.comment_service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping()
    public CommentResponseModel addNewComment(@RequestBody CommentRequestModel comment) {
        return commentService.addNewComment(comment);
    }

    @GetMapping("{comment-id}")
    public CommentResponseModel getCommentById(@PathVariable(value = "comment-id") String id) {
        return commentService.getCommentById(id);
    }

    @GetMapping()
    public List<CommentResponseModel> getComments() {
        return commentService.getComments();
    }

    @PutMapping()
    public CommentResponseModel updateComment(@RequestBody CommentRequestModel request) {
        return commentService.updateComment(request);
    }

    @DeleteMapping("/{comment-id}")
    public void removeComment(@PathVariable(value = "comment-id") String id) {
        commentService.removeComment(id);
    }
}
