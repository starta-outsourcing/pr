package org.example.taskflowd.domain.comment.repository;

import org.example.taskflowd.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
