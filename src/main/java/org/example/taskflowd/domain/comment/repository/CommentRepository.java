package org.example.taskflowd.domain.comment.repository;

import org.example.taskflowd.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 삭제 필터링 ID 탐색
    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

    // Task의 Comment 목록 조회
    Page<Comment> findByTaskIdAndDeletedAtIsNull(Long taskId, Pageable pageable);
    List<Comment> findByTaskIdAndDeletedAtIsNull(Long taskId, Sort sort);
}
