package org.example.taskflowd.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.taskflowd.common.entity.BaseEntity;
import org.example.taskflowd.domain.comment.dto.request.CreateCommentRequest;
import org.example.taskflowd.domain.comment.exception.CommentErrorCode;
import org.example.taskflowd.domain.comment.exception.InvalidCommentException;
import org.example.taskflowd.domain.task.entity.Task;
import org.example.taskflowd.domain.user.entity.User;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "comments",
        indexes = {
                @Index(name = "idx_contents_writer", columnList = "writer_id"),
                @Index(name = "idx_contents_task", columnList = "task_id"),
                @Index(name = "idx_contents_parent", columnList = "parent_id"),}
)
@Getter
@ToString(exclude = {"writer","task","parent","children"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_task"))
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",
            foreignKey = @ForeignKey(name = "fk_comments_parent"))
    private Comment parent;

    /* ========== 역방향 컬렉션 (읽기전용) ========== */
    @SQLRestriction("deleted_at IS NULL")
    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.PERSIST,
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    @OrderBy("createdAt ASC")
    @BatchSize(size = 50)
    private List<Comment> children = new ArrayList<>();


    /* ========== 종속관계 ========== */
    public void addChild(Comment child) {
        children.add(child);
        child.parent = this;
    }
    public void removeChild(Comment child) {
        children.remove(child);
        child.parent = null;
    }
    private void setParent(Comment parent) {
        if (parent == this) throw new InvalidCommentException(CommentErrorCode.CMT_PARENT_IS_ME);
        if (parent != null && parent.task != this.task) {
            throw new InvalidCommentException(CommentErrorCode.CMT_TASK_MISMATCH);
        }
        this.parent = parent;
        if (parent != null) parent.children.add(this);
    }

    /* ========== 유틸 메소드 ========== */
    public boolean isEdited() {
        return !Objects.equals(this.getCreatedAt(), this.getUpdatedAt());
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void setTask(Task task) { this.task = task; }

    /* ========== 정적 팩토리 메서드 ========== */
    @Builder(builderMethodName = "rootBuilder", builderClassName = "RootBuilder")
    private Comment(String content, User writer, Task task) {
        this.content = content;
        this.writer = writer;
        this.task = task;
    }

    @Builder(builderMethodName = "replyBuilder", builderClassName = "ReplyBuilder")
    private Comment(String content, User writer, Task task, Comment parent) {
        this.content = content;
        this.writer = writer;
        this.task = task;
        setParent(parent);
    }
}
