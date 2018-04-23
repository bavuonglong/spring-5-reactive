package com.codeko.spring5reactive.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String id) {
        super("Post:" + id +" is not found.");
        log.error("Post:" + id +" is not found.");
    }
}
