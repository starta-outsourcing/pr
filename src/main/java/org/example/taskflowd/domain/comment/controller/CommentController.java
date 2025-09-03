package org.example.taskflowd.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflowd.common.dto.response.ApiResponse;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.example.taskflowd.domain.comment.dto.request.UpdateCommentRequest;
import org.example.taskflowd.domain.comment.dto.response.CommentResponse;
import org.example.taskflowd.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<?> createComment(@PathVariable Long taskId,
                                        @RequestParam CreateCommentRequest createCommentRequest) {

        CommentResponse createdComment = commentService.createComment(createCommentRequest, taskId, 1L);
        return ApiResponse.ofSuccess("댓글이 생성되었습니다.", createdComment);
    }

    @PutMapping("/{commentId}")
    public ApiResponse<?> updateComment(@PathVariable Long taskId,
                                        @PathVariable Long commentId,
                                        @RequestParam UpdateCommentRequest updateCommentRequest) {

        CommentResponse updatedComment = commentService.updateComment(updateCommentRequest, taskId, commentId);
        return ApiResponse.ofSuccess("댓글이 수정되었습니다.", updatedComment);
    }
}
