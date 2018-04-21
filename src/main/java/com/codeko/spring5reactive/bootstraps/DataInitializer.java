package com.codeko.spring5reactive.bootstraps;

import com.codeko.spring5reactive.models.Post;
import com.codeko.spring5reactive.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class DataInitializer {

    private PostRepository postRepository;

    public DataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void init() {
        initPosts();
    }

    private void initPosts() {
        log.info("Start init posts data");
        this.postRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> this.postRepository.save(
                                                Post.builder().title(title).content("Content of " + title).build()
                                        )
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done post init")
                );
    }
}
