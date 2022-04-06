package com.springboot.PracticleProject.service.post_service;

import com.springboot.PracticleProject.exception.ValidationHandler;
import com.springboot.PracticleProject.model.comment_model.Comment;
import com.springboot.PracticleProject.model.post_model.Post;
import com.springboot.PracticleProject.model.post_model.PostRequestModel;
import com.springboot.PracticleProject.model.post_model.PostResponseModel;
import com.springboot.PracticleProject.repository.post_repository.PostRepository;
import com.springboot.PracticleProject.service.comment_service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Lazy
    @Autowired
    private CommentService commentService;

    public PostResponseModel addProject(PostRequestModel postRequest) {
        if (postRequest == null) {
            throw new ValidationHandler("Post Request Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (postRequest.getPostName() == null || postRequest.getPostName().isEmpty()) {
            throw new ValidationHandler("Post Name Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (postRequest.getCommentList() == null || postRequest.getCommentList().isEmpty()) {
            throw new ValidationHandler("CommentsList Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        Post post = new Post();
        post.setId(id);
        post.setPostName(postRequest.getPostName());
        postRequest.getCommentList().forEach(comment -> {
            Comment newComment = new Comment();
            UUID uuidPost = UUID.randomUUID();
            String postId = uuidPost.toString();
            newComment.setId(postId);
            if (comment.getCommentMessage() == null || comment.getCommentMessage().isEmpty()) {
                throw new ValidationHandler("CommentsMessage Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
            }
            newComment.setCommentMessage(comment.getCommentMessage());
            post.addComment(newComment);
        });
        return setPostResponseModel(postRepository.save(post));
    }

    public List<PostResponseModel> getAllPosts() {
        Iterable<Post> posts = postRepository.findAll();
        List<PostResponseModel> postResponseModelList = new ArrayList<>();
        posts.forEach(post -> {
            postResponseModelList.add(setPostResponseModel(post));
        });
        return postResponseModelList;
    }

    public PostResponseModel getPostById(String id) {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new ValidationHandler("Post Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ValidationHandler("Post Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        return setPostResponseModel(optionalPost.get());
    }

    public Post getPostByPostId(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ValidationHandler("Post Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        return optionalPost.get();
    }

    public PostResponseModel updatePost(PostRequestModel request) {
        if (request == null) {
            throw new ValidationHandler("Post Request Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getPostName() == null || request.getPostName().isEmpty()) {
            throw new ValidationHandler("Post Name Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getCommentList() == null || request.getCommentList().isEmpty()) {
            throw new ValidationHandler("CommentsList Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Post> optionalPost = postRepository.findById(request.getId());
        if (!optionalPost.isPresent()) {
            throw new ValidationHandler("Post Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        Post post = optionalPost.get();
        post.setPostName(request.getPostName());
        request.getCommentList().forEach(comment -> {
            if (comment.getCommentMessage() == null || comment.getCommentMessage().isEmpty()) {
                throw new ValidationHandler("CommentsMessage Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
            }
            if (commentService.findCommentByCommentId(comment.getId())) {
                Comment newComment = commentService.getCommentByCommentId(comment.getId());
                newComment.setCommentMessage(comment.getCommentMessage());
                post.addComment(newComment);
            } else {
                UUID commentUUID = UUID.randomUUID();
                String commentId = commentUUID.toString();
                Comment newComment = new Comment();
                newComment.setId(commentId);
                newComment.setCommentMessage(comment.getCommentMessage());
                post.addComment(newComment);
            }
        });

        return setPostResponseModel(postRepository.save(post));
    }

    public void deletePost(String id) {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new ValidationHandler("Post Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ValidationHandler("Post Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        Post post = new Post();
        List<Comment> commentsList = post.getCommentList();
        postRepository.deleteById(id);
    }

    public boolean findPostByPostId(String id) {
        return postRepository.findById(id).isPresent();
    }

    private PostResponseModel setPostResponseModel(Post request) {
        PostResponseModel response = new PostResponseModel();
        response.setId(request.getId());
        response.setPostName(request.getPostName());
        return response;
    }
}
