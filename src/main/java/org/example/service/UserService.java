package org.example.service;


import org.example.dto.BookDto;
import org.example.dto.CommentDto;
import org.example.dto.UserDto;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.model.User;
import org.example.repository.CommentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;


    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<UserDto> getAll() {
        return this.repository.findAll().stream().map(this::toDTO).toList();
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
        this.repository.deleteById(id);
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

}
