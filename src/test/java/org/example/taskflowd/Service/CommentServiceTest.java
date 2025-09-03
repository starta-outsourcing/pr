package org.example.taskflowd.Service;

import org.example.taskflowd.domain.Task.Entity.Task;
import org.example.taskflowd.domain.Task.Repository.TaskRepository;
import org.example.taskflowd.domain.User.Entity.User;
import org.example.taskflowd.domain.User.Repository.UserRepository;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.example.taskflowd.domain.comment.dto.request.UpdateCommentRequest;
import org.example.taskflowd.domain.comment.dto.response.CommentResponse;
import org.example.taskflowd.domain.comment.entity.Comment;
import org.example.taskflowd.domain.comment.repository.CommentRepository;
import org.example.taskflowd.domain.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("[CommentService - createdComment] 정상 작동 확인")
    @Transactional
    void createComment() {

        CreateCommentRequest createCommentRequest = new CreateCommentRequest("내용", null);
        User user = new User();
        Task task = new Task();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(task, "id", 1L);
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        Comment savedComment = Comment.create(createCommentRequest, task, user);
        ReflectionTestUtils.setField(savedComment, "id", 1L);
        given(commentRepository.save(any(Comment.class))).willReturn(savedComment);

        CommentResponse result = commentService.createComment(createCommentRequest, 1L, 1L);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("[CommentService - updateComment] 정상 작동 확인")
    @Transactional
    void updateComment() {

        CreateCommentRequest createCommentRequest = new CreateCommentRequest("내용", null);
        User user = new User();
        Task task = new Task();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(task, "id", 1L);
        Comment comment = Comment.create(createCommentRequest, task, user);

        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("업데이트");
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        CommentResponse result = commentService.updateComment(updateCommentRequest, 1L, 1L);

        assertThat(result).isNotNull();
    }
}
