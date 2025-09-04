package org.example.taskflowd.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.task.dto.TaskDescriptor;
import org.example.taskflowd.domain.task.entity.Task;
import org.example.taskflowd.domain.task.exception.InvalidTaskException;
import org.example.taskflowd.domain.task.exception.TaskErrorCode;
import org.example.taskflowd.domain.task.mapper.TaskMapper;
import org.example.taskflowd.domain.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskInternalServiceImpl implements TaskInternalService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDescriptor getTaskDescriptorByIdOrThrow(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id)
                .map(taskMapper::toDescriptor)
                .orElseThrow(() -> new InvalidTaskException(TaskErrorCode.TSK_SEARCH_FAILED_INVALID_ID));
    }

    @Override
    public Task getTaskByIdOrThrow(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new InvalidTaskException(TaskErrorCode.TSK_SEARCH_FAILED_INVALID_ID));
    }
}
