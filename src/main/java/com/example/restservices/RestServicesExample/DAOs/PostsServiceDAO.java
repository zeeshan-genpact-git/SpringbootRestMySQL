package com.example.restservices.RestServicesExample.DAOs;

import com.example.restservices.RestServicesExample.beans.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsServiceDAO extends JpaRepository<Post, Integer> {
}
