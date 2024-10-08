package org.daniel.managementspringapp.service.impl;

import org.daniel.managementspringapp.dto.TaskDto;
import org.daniel.managementspringapp.exception.NoFieldsToUpdateException;
import org.daniel.managementspringapp.exception.ResourceNotFoundException;
import org.daniel.managementspringapp.mapper.TaskMapper;
import org.daniel.managementspringapp.model.Task;
import org.daniel.managementspringapp.repository.TaskRepository;
import org.daniel.managementspringapp.service.interfaces.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import static org.daniel.managementspringapp.utils.ResourceAuthorizationHandler.verifyUserCanModifyResource;

@Service
public class TaskService implements CrudService<TaskDto, Long> {
    TaskRepository taskRepository;
    TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getAll() {
        return taskMapper.fromTaskList(taskRepository.findAll());
    }

    @Override
    public TaskDto get(Long id) {
        return taskMapper.fromTask(taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found"))
        );
    }

    @Transactional
    @Override
    public TaskDto add(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.fromTask(savedTask);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Task taskToDelete = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found")
        );
        verifyUserCanModifyResource(taskToDelete.getUser().getId());

        taskRepository.deleteById(id);
    }

    @Transactional
    @Override
    public TaskDto update(TaskDto taskDto) {
        Task existingTask = taskRepository.findById(taskDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Task not found")
        );
        verifyUserCanModifyResource(existingTask.getUser().getId());

        Optional.ofNullable(taskDto.getName()).ifPresent(existingTask::setName);
        Optional.ofNullable(taskDto.getDescription()).ifPresent(existingTask::setDescription);
        Optional.ofNullable(taskDto.getStatus()).ifPresent(existingTask::setStatus);
        Optional.ofNullable(taskDto.getDuration()).ifPresent(existingTask::setDuration);

        return taskMapper.fromTask(taskRepository.save(existingTask));
    }
}
