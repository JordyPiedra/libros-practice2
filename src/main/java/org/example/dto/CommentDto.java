package org.example.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CommentDto {

	private long id;

	private String body;
	private int points;

	private Book book;

	private User user;

}