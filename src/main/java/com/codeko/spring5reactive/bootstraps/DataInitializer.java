package com.codeko.spring5reactive.bootstraps;

import com.codeko.spring5reactive.models.Post;
import com.codeko.spring5reactive.models.User;
import com.codeko.spring5reactive.repositories.PostRepository;
import com.codeko.spring5reactive.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(PostRepository postRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void init() {
        initPosts();
        initUsers();
    }

    private void initUsers() {
        this.userRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("user", "admin")
                                .flatMap(
                                        username -> {
                                            List<String> roles = "user".equals(username)
                                                    ? Arrays.asList("ROLE_USER")
                                                    : Arrays.asList("ROLE_USER", "ROLE_ADMIN");
                                            User user = User.builder()
                                                    .roles(roles)
                                                    .username(username)
                                                    .password(passwordEncoder.encode("password"))
                                                    .email(username + "@example.com")
                                                    .build();
                                            return this.userRepository.save(user);
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done users initialization...")
                );
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
