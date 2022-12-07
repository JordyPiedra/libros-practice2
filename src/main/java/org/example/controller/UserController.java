package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.dto.BookDto;
import org.example.dto.CommentResponseDto;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found users",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @GetMapping("/")
    public Page<UserDto> getAll(Pageable page) {
        return this.service.getAll(page);
    }

    @Operation(summary = "Get user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found user",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public UserDto getUser(
            @Parameter(description = "id of user to be searched")
            @PathVariable long id) {
        return this.service.getById(id);
    }

    @Operation(summary = "Add user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not added",
                    content = @Content
            )
    })
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "Insert user")
            @RequestBody UserDto userDto) {
        UserDto responseDto = this.service.create(userDto);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }


    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id of user supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not updated",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "User Id")
            @PathVariable long id,
            @Parameter(description = "User data")
            @RequestBody UserDto userDto) {
        UserDto responseDto = this.service.update(id, userDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "User deleted")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id of user supplied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not deleted",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/comments/")
    public List<CommentResponseDto> getAllCommentsByUser(@PathVariable long id) {
        return this.service.getCommentsByUser(id);
    }
}
