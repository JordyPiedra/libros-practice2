package org.example.dto;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class BookDto {

	private long id;

	private String summary;

	private String author;

	private String editorial;

	private int publicationYear;

	private List<Comment> comments = new ArrayList<>();

}