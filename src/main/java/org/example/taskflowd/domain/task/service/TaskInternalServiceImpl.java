package org.example.taskflowd.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.task.dto.TaskDescriptor;
import org.example.taskflowd.domain.task.exception.InvalidTaskException;
import org.example.taskflowd.domain.task.exception.TaskErrorCode;
import org.example.taskflowd.domain.task.mapper.TaskMapper;
import org.example.taskflowd.domain.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskInternalServiceImpl implements TaskInternalService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDescriptor getTaskDescriptorByIdOrThrow(Long id) {
        return taskRepository.findByIdAndDeletedIsFalse(id)
                .map(taskMapper::toDescriptor)
                .orElseThrow(() -> new InvalidTaskException(TaskErrorCode.TSK_SEARCH_FAILED_INVALID_ID));
    }
}
