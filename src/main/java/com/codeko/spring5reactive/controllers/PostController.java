package com.codeko.spring5reactive.controllers;

import com.codeko.spring5reactive.models.Post;
import com.codeko.spring5reactive.repositories.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("")
    public Flux<Post> getPosts() {
        return this.postRepository.findAll();
    }


}
