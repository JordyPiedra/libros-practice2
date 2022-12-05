package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentDto {

    private long id;

    private String body;
    private int points;

}