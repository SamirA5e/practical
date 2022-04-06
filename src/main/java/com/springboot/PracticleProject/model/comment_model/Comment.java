package com.springboot.PracticleProject.model.comment_model;

import com.springboot.PracticleProject.model.post_model.Post;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id()
    private String id;

    @Column(name = "commentMessage")
    private String commentMessage;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "post_id")
    private Post post;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}