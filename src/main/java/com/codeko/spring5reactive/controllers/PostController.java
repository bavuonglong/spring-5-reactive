package com.codeko.spring5reactive.controllers;

import com.codeko.spring5reactive.exceptions.PostNotFoundException;
import com.codeko.spring5reactive.models.Post;
import com.codeko.spring5reactive.repositories.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Post> createPost(@RequestBody Post post) {
        return this.postRepository.save(post);
    }

    @GetMapping("/{id}")
    public Mono<Post> getPostById(@PathVariable String id) {
        return this.postRepository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }

    @PutMapping("/{id}")
    public Mono<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        return this.postRepository
                .findById(id)
                .map(p -> {
                    p.setContent(post.getContent());
                    p.setTitle(post.getTitle());

                    return p;
                })
                .flatMap(p -> this.postRepository.save(p));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePostById(@PathVariable String id) {
        return this.postRepository.deleteById(id);
    }
}
