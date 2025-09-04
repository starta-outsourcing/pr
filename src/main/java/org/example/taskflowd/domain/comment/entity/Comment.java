package org.example.taskflowd.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.taskflowd.common.entity.BaseEntity;
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
                @Index(name = "idx_tasks_title", columnList = "title"),
                @Index(name = "idx_tasks_writer", columnList = "writer_id"),
                @Index(name = "idx_tasks_assignee", columnList = "assignee_id"),
                @Index(name = "idx_tasks_due_date", columnList = "due_date")
        }
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
    public void changeParent(Comment newParent) {
        if (newParent == this) throw new IllegalArgumentException("self parent");
        this.parent = newParent;
    }

    /* ========== 유틸 메소드 ========== */
    public boolean isEdited() {
        return !Objects.equals(this.getCreatedAt(), this.getUpdatedAt());
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void setTask(Task task) { this.task = task; }
}
