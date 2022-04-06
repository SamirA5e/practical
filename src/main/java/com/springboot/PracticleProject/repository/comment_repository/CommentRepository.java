package com.springboot.PracticleProject.repository.comment_repository;

import com.springboot.PracticleProject.model.comment_model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
}