package com.springboot.PracticleProject.service.comment_service;

import com.springboot.PracticleProject.exception.ValidationHandler;
import com.springboot.PracticleProject.model.comment_model.Comment;
import com.springboot.PracticleProject.model.comment_model.CommentRequestModel;
import com.springboot.PracticleProject.model.comment_model.CommentResponseModel;
import com.springboot.PracticleProject.repository.comment_repository.CommentRepository;
import com.springboot.PracticleProject.service.post_service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    public CommentResponseModel addNewComment(CommentRequestModel request) {
        if (request == null) {
            throw new ValidationHandler("Comment Request Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getCommentMessage() == null || request.getCommentMessage().isEmpty()) {
            throw new ValidationHandler("Comment Name Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getPostId() == null || request.getPostId().isEmpty()) {
            throw new ValidationHandler("Post Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setCommentMessage(request.getCommentMessage());
        comment.setPost(postService.getPostByPostId(request.getPostId()));

        return setCommentResponseModel(commentRepository.save(comment));
    }

    public CommentResponseModel getCommentById(String id) {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new ValidationHandler("Comment Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            throw new ValidationHandler("Comment Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        return setCommentResponseModel(optionalComment.get());
    }

    public Comment getCommentByCommentId(String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            throw new ValidationHandler("Comment Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        return optionalComment.get();
    }

    public List<CommentResponseModel> getComments() {
        Iterable<Comment> comments = commentRepository.findAll();
        List<CommentResponseModel> commentResponseModelList = new ArrayList<>();
        comments.forEach(comment -> {
            commentResponseModelList.add(setCommentResponseModel(comment));
        });
        return commentResponseModelList;
    }

    public CommentResponseModel updateComment(CommentRequestModel request) {
        if (request == null) {
            throw new ValidationHandler("Comment Request Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getId() == null || request.getId().isEmpty()) {
            throw new ValidationHandler("Comment Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getCommentMessage() == null || request.getCommentMessage().isEmpty()) {
            throw new ValidationHandler("Comment Name Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getPostId() == null || request.getPostId().isEmpty()) {
            throw new ValidationHandler("Post Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        if (!postService.findPostByPostId(request.getPostId())) {
            throw new ValidationHandler("Post Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        Optional<Comment> optionalComment = commentRepository.findById(request.getId());
        if (!optionalComment.isPresent()) {
            throw new ValidationHandler("Comment Id Is Not Present In Database , Please Provide Valid Comment Id", HttpStatus.BAD_REQUEST);
        }
        Comment comment = optionalComment.get();
        comment.setCommentMessage(request.getCommentMessage());
        return setCommentResponseModel(commentRepository.save(comment));
    }

    public void removeComment(String id) {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new ValidationHandler("Comment Id Can't Be Null Or Empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            throw new ValidationHandler("Comment Id Is Not Present In Database , Please Provide Valid Post Id", HttpStatus.BAD_REQUEST);
        }
        commentRepository.deleteById(id);
    }

    public boolean findCommentByCommentId(String id) {
        return commentRepository.findById(id).isPresent();
    }

    private CommentResponseModel setCommentResponseModel(Comment request) {
        CommentResponseModel response = new CommentResponseModel();
        response.setId(request.getId());
        response.setCommentMessage(request.getCommentMessage());
        return response;
    }

}