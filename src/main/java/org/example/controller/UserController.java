package org.example.controller;


import org.example.dto.BookDto;
import org.example.dto.UserDto;
import org.example.service.BookService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<UserDto> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getBook(@PathVariable long id) {
        return this.service.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createBook(@RequestBody UserDto userDto) {
        UserDto responseDto = this.service.create(userDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateBook(@PathVariable long id, @RequestBody UserDto userDto) {
        UserDto responseDto = this.service.update(id, userDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteBook(@PathVariable long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
