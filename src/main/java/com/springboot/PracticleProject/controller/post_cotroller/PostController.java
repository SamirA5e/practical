package com.springboot.PracticleProject.controller.post_cotroller;

import com.springboot.PracticleProject.model.post_model.PostRequestModel;
import com.springboot.PracticleProject.model.post_model.PostResponseModel;
import com.springboot.PracticleProject.service.post_service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping()
    public PostResponseModel addPost(@RequestBody PostRequestModel postRequest) {
        return postService.addProject(postRequest);
    }

    @GetMapping()
    public List<PostResponseModel> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{post-id}")
    public PostResponseModel getPostById(@PathVariable(value = "post-id") String id) {
        return postService.getPostById(id);
    }

    @PutMapping()
    public PostResponseModel updatePost(@RequestBody PostRequestModel requestPost) {
        return postService.updatePost(requestPost);
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(@PathVariable(value = "post-id") String id) {
        postService.deletePost(id);
    }
}