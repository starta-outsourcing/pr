package org.example.taskflowd.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflowd.common.entity.BaseEntity;
import org.example.taskflowd.domain.Task.Entity.Task;
import org.example.taskflowd.domain.User.Entity.User;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String description;
    @JoinColumn(nullable = false)
    private boolean isEdited;

    // 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    public static Comment create(CreateCommentRequest createCommentRequest, Task task, User user) {
        return new Comment(
                null,
                createCommentRequest.content(),
                false,
                null,
                task,
                user
        );
    }
}
