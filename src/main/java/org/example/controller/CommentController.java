package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.dto.BookDto;
import org.example.dto.CommentDto;
import org.example.dto.CommentResponseDto;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/books/{idBook}")
public class CommentController {


    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @Operation(summary = "Get all comments by Book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found comments",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id comment supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comments not found",
                    content = @Content
            )
    })
    @GetMapping("/comments")
    public Page<CommentResponseDto> getAll(
            @Parameter(description = "Comments by Book Id")
            @PathVariable long idBook,
            Pageable page) {
        return this.service.getAll(idBook, page);
    }

    @Operation(summary = "Get comment by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found comments by Id",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id comment supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = @Content
            )
    })
    @GetMapping("/comments/{id}")
    public CommentResponseDto getComment(
            @Parameter(description = "id of book to be searched")
            @PathVariable long idBook,
            @Parameter(description = "id of comment to be searched")
            @PathVariable long id) {
        return this.service.getById(idBook, id);
    }

    @Operation(summary = "Add comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not added",
                    content = @Content
            )
    })
    @PostMapping("/comments/")
    public ResponseEntity<CommentResponseDto> createComment(
            @Parameter(description = "Book Id")
            @PathVariable long idBook,
            @Parameter(description = "Insert data")
            @RequestBody CommentDto commentDto) {
        CommentResponseDto responseDto = this.service.create(idBook, commentDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id of comment supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not updated",
                    content = @Content
            )
    })
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @Parameter(description = "Book Id")
            @PathVariable long idBook,
            @Parameter(description = "Comment Id")
            @PathVariable long id,
            @RequestBody CommentDto commentDto) {
        CommentResponseDto responseDto = this.service.update(idBook, id, commentDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Comment deleted")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comment id supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not deleted",
                    content = @Content
            )
    })
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<BookDto> deleteComment(
            @Parameter(description = "Book Id")
            @PathVariable long idBook,
            @Parameter(description = "Comment Id")
            @PathVariable long id) {
        this.service.delete(idBook, id);
        return ResponseEntity.ok().build();
    }
}
