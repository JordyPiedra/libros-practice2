package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all books registered")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found books",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Books not found",
                    content = @Content
            )
    })

    @GetMapping("/")
    public List<BookDto> getAll() {
        return this.service.getAll();
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found book",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public BookDto getBook(
            @Parameter(description = "id of book to be searched")
            @PathVariable long id) {
        return this.service.getById(id);
    }

    @Operation(summary = "Add a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not added",
                    content = @Content
            )
    })
    @PostMapping("/")
    public ResponseEntity<BookDto> createBook(
            @Parameter(description = "Insert data")
            @RequestBody BookDto bookDto) {
        BookDto responseDto = this.service.create(bookDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @Operation(summary = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id of book supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not updated",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @Parameter(description = "Id Book")
            @PathVariable long id, @RequestBody BookDto bookDto) {
        BookDto responseDto = this.service.update(id, bookDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Book deleted")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id of book supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not deleted",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(
            @Parameter(description = "Id Book")
            @PathVariable long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
