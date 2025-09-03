package org.example.taskflowd.domain.comment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.Task.Entity.Task;
import org.example.taskflowd.domain.Task.Repository.TaskRepository;
import org.example.taskflowd.domain.User.Entity.User;
import org.example.taskflowd.domain.User.Repository.UserRepository;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.example.taskflowd.domain.comment.dto.request.UpdateCommentRequest;
import org.example.taskflowd.domain.comment.dto.response.CommentResponse;
import org.example.taskflowd.domain.comment.entity.Comment;
import org.example.taskflowd.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public CommentResponse createComment(CreateCommentRequest createCommentRequest, Long taskId, Long userId) {

        // 작업 및 작성자
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = Comment.create(createCommentRequest, task, user);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(UpdateCommentRequest updateCommentRequest, Long taskId, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        if (!comment.getTask().getId().equals(taskId)) { throw new EntityNotFoundException("Task not found"); }

        comment.update(updateCommentRequest);

        return CommentResponse.of(comment);
    }
}
