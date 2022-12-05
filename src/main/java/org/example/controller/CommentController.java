package org.example.controller;


import org.example.dto.BookDto;
import org.example.dto.CommentDto;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping
public class CommentController {


    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/books/{idBook}/comments")
    public List<CommentDto> getAll(@PathVariable long idBook) {

        return this.service.getAll(idBook);
    }

    @GetMapping("/books/{idBook}/comments/{id}")
    public CommentDto getComment(@PathVariable long idBook, @PathVariable long id) {
        return this.service.getById(idBook, id);
    }

    @PostMapping("/books/{idBook}/comments/")
    public ResponseEntity<CommentDto> createBook(@PathVariable long idBook, @RequestBody CommentDto commentDto) {
        CommentDto responseDto = this.service.create(idBook, commentDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @PutMapping("/books/{idBook}/comments/{id}")
    public ResponseEntity<CommentDto> updateBook(@PathVariable long idBook, @PathVariable long id, @RequestBody CommentDto commentDto) {
        commentDto.setId(id);
        CommentDto responseDto = this.service.update(idBook, commentDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
