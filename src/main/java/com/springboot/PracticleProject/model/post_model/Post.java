package com.springboot.PracticleProject.model.post_model;


import com.springboot.PracticleProject.model.comment_model.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    private String id;

    @Column(name = "post_name")
    private String postName;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        commentList.add((comment));
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setPost(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}