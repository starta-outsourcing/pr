package org.example.taskflowd.domain.task.mapper;

import org.example.taskflowd.domain.task.dto.TaskDescriptor;
import org.example.taskflowd.domain.task.dto.request.TaskCreateRequest;
import org.example.taskflowd.domain.task.dto.response.TaskCreateResponse;
import org.example.taskflowd.domain.task.dto.response.TaskDetailResponse;
import org.example.taskflowd.domain.task.dto.response.TaskListItemResponse;
import org.example.taskflowd.domain.task.entity.Task;
import org.example.taskflowd.domain.user.dto.mapper.UserMapper;
import org.example.taskflowd.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toEntity(TaskCreateRequest taskCreateRequest, User assignee) {
        return Task.builder().
                title(taskCreateRequest.title()).
                description(taskCreateRequest.description()).
                dueDate(taskCreateRequest.dueDate()).
                priority(taskCreateRequest.priority()).
                assignee(assignee).
                build();
    }

    public TaskDescriptor toDescriptor(Task task) {
        return TaskDescriptor.toDescriptor(
                task.getId(), task.getTitle(), task.getDescription(),
                task.getWriter().getId(), task.getAssignee().getId(),
                task.getStatus(), task.getPriority(),
                task.getDueDate(),task.getCreatedAt(), task.getUpdatedAt()
        );
    }

    public TaskCreateResponse toCreateResponse(Task task) {
        return TaskCreateResponse.toDto(
                task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
                task.getPriority().getCode(), task.getStatus().getCode(),
                task.getAssignee().getId(), UserMapper.toResponseDto(task.getAssignee()),
                task.getCreatedAt(), task.getUpdatedAt()
        );
    }

    public TaskListItemResponse toListItemResponse(Task task) {
        return TaskListItemResponse.toDto(
                task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
                task.getPriority().getCode(), task.getStatus().getCode(),
                task.getAssignee().getId(), UserMapper.toResponseDto(task.getAssignee()),
                task.getCreatedAt(), task.getUpdatedAt()
        );
    }

    public TaskDetailResponse toDetailResponse(Task task) {
        return TaskDetailResponse.toDto(
                task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
                task.getPriority().getCode(), task.getStatus().getCode(),
                task.getAssignee().getId(), UserMapper.toResponseDto(task.getAssignee()),
                task.getCreatedAt(), task.getUpdatedAt()
        );
    }
}
