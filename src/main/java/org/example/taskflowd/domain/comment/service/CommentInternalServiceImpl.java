package org.example.taskflowd.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.comment.entity.Comment;
import org.example.taskflowd.domain.comment.exception.CommentErrorCode;
import org.example.taskflowd.domain.comment.repository.CommentRepository;
import org.example.taskflowd.domain.task.exception.InvalidTaskException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentInternalServiceImpl implements CommentInternalService {
    private final CommentRepository commentRepository;

    @Override
    public Comment getCommentByIdOrThrow(Long id) {
        return commentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new InvalidTaskException(CommentErrorCode.CMT_SEARCH_FAILED_INVALID_ID));
    }
}
