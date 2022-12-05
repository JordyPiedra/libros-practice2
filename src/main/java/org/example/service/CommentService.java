package org.example.service;


import org.example.dto.CommentDto;
import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;


    @Autowired
    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }


    public List<CommentDto> getAll(long idBook) {
        return this.repository.findAllByBookId(idBook).stream().map(this::toDTO).toList();
    }

    public CommentDto getById(long id) {
        return this.toDTO(this.repository.findById(id).orElseThrow());
    }

    public CommentDto create(CommentDto commentDto) {
        Comment comment = this.repository.save(this.toDomain(commentDto));
        return this.toDTO(comment);
    }

    public CommentDto update(Long id, CommentDto commentDto) {
        commentDto.setId(id);
        Comment comment = this.repository.save(this.toDomain(commentDto));
        return this.toDTO(comment);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    private Comment toDomain(CommentDto commentDto) {
        return Comment.builder()
                .body(commentDto.getBody())
                .points(commentDto.getPoints())
                .book(commentDto.getBook())
                .user(commentDto.getUser()).build();
    }

    private CommentDto toDTO(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .body(comment.getBody())
                .points(comment.getPoints())
                .book(comment.getBook())
                .user(comment.getUser()).build();
    }

}
