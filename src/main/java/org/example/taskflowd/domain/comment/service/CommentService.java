package org.example.taskflowd.domain.comment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.comment.dto.response.CommentListResponse;
import org.example.taskflowd.domain.task.entity.Task;
import org.example.taskflowd.domain.task.repository.TaskRepository;
import org.example.taskflowd.domain.user.entity.User;
import org.example.taskflowd.domain.user.repository.UserRepository;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.example.taskflowd.domain.comment.dto.request.UpdateCommentRequest;
import org.example.taskflowd.domain.comment.dto.response.CommentResponse;
import org.example.taskflowd.domain.comment.entity.Comment;
import org.example.taskflowd.domain.comment.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public CommentListResponse getComments(int page, int size, String sort) {

        Sort sortDirection = Sort.by(sort.equals("newest") ? Sort.Order.asc("id") : Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection);

        Page<Comment> commentPage = commentRepository.findAll(pageRequest);

        return CommentListResponse.of(commentPage.map(CommentResponse::of).toList());
    }

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
    public CommentResponse updateComment(UpdateCommentRequest updateCommentRequest, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.updateComment(updateCommentRequest.content());

        return CommentResponse.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.delete();
    }
}
