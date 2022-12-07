package org.example.repository;

import org.example.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByBookId(long bookId, Pageable page);

    Comment findByBookIdAndId(long bookId, long id);


}
