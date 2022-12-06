package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	private String title;

	private String summary;

	private String author;

	private String editorial;

	private int publicationYear;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)	private List<Comment> comments = new ArrayList<>();

}