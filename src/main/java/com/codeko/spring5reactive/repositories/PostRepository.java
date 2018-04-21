package com.codeko.spring5reactive.repositories;

import com.codeko.spring5reactive.models.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}
