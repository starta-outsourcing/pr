package org.example.taskflowd.domain.comment.service;

import org.example.taskflowd.domain.comment.entity.Comment;

public interface CommentInternalService {
    Comment getCommentByIdOrThrow(Long id);
}

