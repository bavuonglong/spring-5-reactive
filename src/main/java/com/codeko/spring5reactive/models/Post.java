package com.codeko.spring5reactive.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
