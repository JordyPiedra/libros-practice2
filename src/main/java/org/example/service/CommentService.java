package org.example.service;


import org.example.dto.CommentDto;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.repository.BookRepository;
import org.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository repository;

    private final BookRepository bookRepository;


    @Autowired
    public CommentService(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    public List<CommentDto> getAll(long idBook) {
        return this.repository.findAllByBookId(idBook).stream().map(this::toDTO).toList();
    }

    public CommentDto getById(long idBook , long id) {
      Comment  comment = this.repository.findByBookIdAndId(idBook,id);
      if (comment == null)
          throw new NoSuchElementException("Comment not found");
      return this.toDTO(comment);
    }

    public CommentDto create(long idBook ,CommentDto commentDto) {
        Comment comment = this.toDomain(commentDto);
        this.setBook(comment,idBook);
        this.repository.save(comment);
        return this.toDTO(comment);
    }

    public CommentDto update(Long idBook, CommentDto commentDto) {
        Comment comment = this.toDomain(commentDto);
        this.setBook(comment,idBook);
        this.repository.save(this.toDomain(commentDto));
        return this.toDTO(comment);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    private void setBook(Comment comment, long id){
        Book book = this.bookRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Book not found for this id :: " + id));
        comment.setBook(book);
    }

    private Comment toDomain(CommentDto commentDto) {
        return Comment.builder()
                .body(commentDto.getBody())
                .points(commentDto.getPoints())
                .build();
    }

    private CommentDto toDTO(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .body(comment.getBody())
                .points(comment.getPoints())
                .build();
    }

}
