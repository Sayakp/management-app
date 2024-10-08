package org.daniel.managementspringapp.service;


import org.daniel.managementspringapp.dto.TaskDto;
import org.daniel.managementspringapp.exception.ResourceNotFoundException;
import org.daniel.managementspringapp.mapper.TaskMapper;
import org.daniel.managementspringapp.model.Task;
import org.daniel.managementspringapp.model.User;
import org.daniel.managementspringapp.repository.TaskRepository;
import org.daniel.managementspringapp.service.impl.TaskService;
import org.daniel.managementspringapp.utils.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDto taskDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setStatus("OPEN");
        task.setDuration(60);
        task.setUser(user);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setName("Test Task");
        taskDto.setDescription("Test Description");
        taskDto.setStatus("OPEN");
        taskDto.setDuration(60);
    }

    @Test
    void testGetAll() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.fromTaskList(any())).thenReturn(Arrays.asList(taskDto));

        List<TaskDto> result = taskService.getAll();

        assertEquals(1, result.size());
        assertEquals(taskDto.getName(), result.get(0).getName());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGet() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.fromTask(task)).thenReturn(taskDto);

        TaskDto result = taskService.get(1L);

        assertEquals(taskDto.getName(), result.getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testAdd() {
        when(taskMapper.toTask(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromTask(task)).thenReturn(taskDto);

        TaskDto result = taskService.add(taskDto);

        assertEquals(taskDto.getName(), result.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @WithMockCustomUser
    @Test
    void testDelete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.delete(1L));

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @WithMockCustomUser
    @Test
    void testUpdate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.fromTask(task)).thenReturn(taskDto);
        when(taskRepository.save(task)).thenReturn(task);

        TaskDto result = taskService.update(taskDto);

        assertEquals(taskDto.getName(), result.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> taskService.get(1L));
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testDeleteNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> taskService.delete(1L));
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testUpdateNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> taskService.update(taskDto));
        assertEquals("Task not found", exception.getMessage());
    }
}