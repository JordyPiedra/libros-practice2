package org.example.dto;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {

    private long id;

    private String nick;
    private String email;

}
