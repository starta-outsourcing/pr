package org.example.taskflowd.domain.task.service;

import org.example.taskflowd.domain.task.dto.TaskDescriptor;

import java.util.Optional;

public interface TaskInternalService {
    TaskDescriptor getTaskDescriptorByIdOrThrow(Long id);
}

