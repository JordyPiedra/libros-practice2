package org.example.service;


import org.example.dto.CommentResponseDto;
import org.example.dto.UserDto;
import org.example.model.Comment;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository repository;


    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public Page<UserDto> getAll(Pageable page) {
        return this.repository.findAll(page).map(this::toDTO);
    }

    public UserDto getById(long id) {
        return this.toDTO(this.repository.findById(id).orElseThrow());
    }


    public UserDto create(UserDto userDto) {
        User user = this.repository.save(this.toDomain(userDto));
        return this.toDTO(user);
    }

    public UserDto update(Long id, UserDto userDto) {
        userDto.setId(id);
        User user = this.repository.save(this.toDomain(userDto));
        return this.toDTO(user);
    }


    public void delete(Long id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found for this id :: " + id));
        if (user.getComments() != null && !user.getComments().isEmpty()) {
            throw new RuntimeException("User must be empty comments");
        }
        this.repository.deleteById(id);

    }

    public List<CommentResponseDto> getCommentsByUser(long id) {
        User user = this.repository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found for this id :: " + id));
        assert (user.getComments() != null);
        return user.getComments().stream().map(this::toCommentResponseDto).toList();
    }

    private User toDomain(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .nick(userDto.getNick()).build();
    }

    private UserDto toDTO(User user) {
        return UserDto.builder().id(user.getId())
                .email(user.getEmail())
                .nick(user.getNick()).build();
    }

    private CommentResponseDto toCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder().id(comment.getId())
                .body(comment.getBody())
                .points(comment.getPoints())
                .bookId(comment.getBook().getId())
                .build();
    }

}
