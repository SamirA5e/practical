package com.springboot.PracticleProject.repository.post_repository;

import com.springboot.PracticleProject.model.post_model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,String> {
}
