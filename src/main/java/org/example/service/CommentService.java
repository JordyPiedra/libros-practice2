package org.example.service;


import org.example.dto.CommentDto;
import org.example.dto.CommentResponseDto;
import org.example.dto.UserDto;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.model.User;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public List<CommentResponseDto> getAll(long idBook) {
        return this.repository.findAllByBookId(idBook).stream().map(this::toDTO).toList();
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
