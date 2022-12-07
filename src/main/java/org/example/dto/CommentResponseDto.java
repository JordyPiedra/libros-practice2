package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

    private Long id;

    private String body;
    private int points;

    private Long userId;

    private Long bookId;

}