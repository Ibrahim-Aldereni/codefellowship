package com.example.demo.repositories;

import com.example.demo.modals.ApplicationUser;
import com.example.demo.modals.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {
}
