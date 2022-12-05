package org.example.controller;


import org.example.dto.BookDto;
import org.example.dto.CommentDto;
import org.example.model.Comment;
import org.example.service.BookService;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/books/{idBook}/comments")
public class CommentController {


    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<CommentDto> getAll(@PathVariable long idBook) {

        return this.service.getAll(idBook);
    }

}
