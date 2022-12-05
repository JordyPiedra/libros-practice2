package org.example.controller;


import org.example.dto.BookDto;
import org.example.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/books")
public class BookController {


    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<BookDto> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable long id) {
        return this.service.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto responseDto = this.service.create(bookDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        BookDto responseDto = this.service.update(id, bookDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
