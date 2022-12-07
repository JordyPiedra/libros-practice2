package org.example.service;


import org.example.dto.BookDto;
import org.example.dto.CommentDto;
import org.example.dto.UserDto;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository repository;


    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }


    public List<BookDto> getAll() {
        return this.repository.findAll().stream().map(this::toDTO).toList();
    }

    public BookDto getById(long id) {
        return this.toDTO(this.repository.findById(id).orElseThrow());
    }

    public BookDto create(BookDto bookDto) {
        Book book = this.repository.save(this.toDomain(bookDto));
        return this.toDTO(book);
    }

    public BookDto update(Long id, BookDto bookDto) {
        this.validateBook(id);
        Book book = this.toDomain(bookDto);
        book.setId(id);
        this.repository.save(book);
        return this.toDTO(book);
    }

    public void delete(Long id) {
        this.validateBook(id);
        this.repository.deleteById(id);
    }

    private Book toDomain(BookDto bookDto) {
        return Book.builder().author(bookDto.getAuthor())
                .title(bookDto.getTitle())
                .summary(bookDto.getSummary())
                .editorial(bookDto.getEditorial())
                .publicationYear(bookDto.getPublicationYear()).build();
    }

    private BookDto toDTO(Book book) {
        return BookDto.builder().id(book.getId())
                .title(book.getTitle())
                .summary(book.getSummary())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .editorial(book.getEditorial())
                .comments(book.getComments() == null ? new ArrayList<>() : book.getComments().stream().map(this::toCommentDTO).toList()).build();
    }

    private CommentDto toCommentDTO(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .body(comment.getBody())
                .points(comment.getPoints())
                .user(UserDto.builder().id(comment.getUser().getId())
                        .email(comment.getUser().getEmail())
                        .nick(comment.getUser().getNick()).build())
                .build();
    }

    private Book validateBook(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found for this id :: " + id));
    }
}
