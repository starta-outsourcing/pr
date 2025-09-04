package org.example.taskflowd.domain.task.service;

import org.example.taskflowd.domain.task.dto.TaskDescriptor;
import org.example.taskflowd.domain.task.entity.Task;

public interface TaskInternalService {
    TaskDescriptor getTaskDescriptorByIdOrThrow(Long id);
    Task getTaskByIdOrThrow(Long id);
}

