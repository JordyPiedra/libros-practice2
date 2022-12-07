package org.example.service;


import jakarta.annotation.PostConstruct;
import org.example.dto.CommentDto;
import org.example.dto.CommentResponseDto;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.model.User;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository repository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;


    @Autowired
    public CommentService(CommentRepository repository, BookRepository bookRepository, UserRepository userRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User user1 = User.builder()
                .email("user1@user1.com")
                .nick("user1").build();
        this.userRepository.save(user1);
        for (int i = 0; i < 100; i++) {
            Book book = Book.builder().author("author " + i)
                    .title("title " + i)
                    .summary("summary " + i)
                    .editorial("editorial " + i)
                    .publicationYear(2022).build();
            this.bookRepository.save(book);
            for (int j = 0; j < 100; j++) {
                Comment comment = Comment.builder()
                        .body("Comentario " + j)
                        .points(10)
                        .user(user1)
                        .book(book)
                        .build();
                this.repository.save(comment);
            }
        }

    }

    public Page<CommentResponseDto> getAll(long idBook, Pageable page) {
        return this.repository.findAllByBookId(idBook, page).map(this::toDTO);
    }

    public CommentResponseDto getById(long idBook, long id) {
        Comment comment = this.repository.findByBookIdAndId(idBook, id);
        if (comment == null)
            throw new NoSuchElementException("Comment not found");
        return this.toDTO(comment);
    }

    public CommentResponseDto create(long idBook, CommentDto commentDto) {
        Comment comment = this.toDomain(commentDto);
        this.setBook(comment, idBook);
        this.setUser(comment, commentDto.getUser().getNick());
        this.repository.save(comment);
        return this.toDTO(comment);
    }

    public CommentResponseDto update(long idBook, long id ,CommentDto commentDto) {
        this.validateComment(id);
        Comment comment = this.toDomain(commentDto);
        comment.setId(id);
        this.setBook(comment, idBook);
        this.setUser(comment, commentDto.getUser().getNick());
        this.repository.save(comment);
        return this.toDTO(comment);
    }


    public void delete(Long idBook, Long id) {
        this.bookRepository.findById(idBook)
                .orElseThrow(() -> new NoSuchElementException("Book not found for this id :: " + idBook));
        this.validateComment(id);
        this.repository.deleteById(id);
    }

    private void setBook(Comment comment, long id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found for this id :: " + id));
        comment.setBook(book);
    }

    private void setUser(Comment comment, String nick) {
        User user = this.userRepository.findFirstByNick(nick)
                .orElseThrow(() -> new NoSuchElementException("User not found for this nick :: " + nick));
        comment.setUser(user);
    }


    private Comment toDomain(CommentDto commentDto) {
        return Comment.builder()
                .body(commentDto.getBody())
                .points(commentDto.getPoints())
                .build();
    }

    private CommentResponseDto toDTO(Comment comment) {
        return CommentResponseDto.builder().id(comment.getId())
                .body(comment.getBody())
                .points(comment.getPoints())
                .userId(comment.getUser().getId())
                .bookId(comment.getBook().getId())
                .build();
    }


    private Comment validateComment(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment not found for this id :: " + id));
    }
}
