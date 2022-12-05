package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_app", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nick"),
        @UniqueConstraint(columnNames = "email")
})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nick;
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
