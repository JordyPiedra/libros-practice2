package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class BookDto {

    private long id;

    private String title;

    private String summary;

    private String author;

    private String editorial;

    private int publicationYear;

    private List<CommentDto> comments = new ArrayList<>();

}