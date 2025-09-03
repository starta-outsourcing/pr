package org.example.taskflowd.domain.task.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.taskflowd.common.entity.BaseEntity;
import org.example.taskflowd.domain.task.enums.TaskPriority;
import org.example.taskflowd.domain.task.enums.TaskStatus;
import org.example.taskflowd.domain.user.entity.User;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "tasks",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tasks_title_writer", columnNames = {"title", "writer_id"}),
        },
        indexes = {
                @Index(name = "idx_tasks_title", columnList = "title"),
                @Index(name = "idx_tasks_writer", columnList = "writer_id"),
                @Index(name = "idx_tasks_assignee", columnList = "assignee_id"),
                @Index(name = "idx_tasks_due_date", columnList = "due_date")
        }
)
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 140)
    @Column(name = "title", nullable = false, length = 140)
    private String title;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_assignee"))
    private User assignee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 30, nullable = false)
    private TaskPriority priority;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    // status, priority 기본 설정
    @PrePersist
    private void applyDefaults() {
        if (this.status == null)   this.status = TaskStatus.TODO;       // 예: 기본값
        if (this.priority == null) this.priority = TaskPriority.MEDIUM; // 예: 기본값
    }

    @Builder
    public Task(String title, String description, User writer, User assignee, TaskStatus status, TaskPriority priority, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.assignee = assignee;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public void updateTask(String title, String description, LocalDateTime dueDate, User assignee) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assignee = assignee;
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }

    public void updatePriority(TaskPriority priority) {
        this.priority = priority;
    }
}
