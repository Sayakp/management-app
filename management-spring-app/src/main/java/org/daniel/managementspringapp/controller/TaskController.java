package org.daniel.managementspringapp.controller;

import jakarta.validation.Valid;
import org.daniel.managementspringapp.dto.TaskDto;
import org.daniel.managementspringapp.service.impl.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.get(id));
    }

    @PostMapping()
    public ResponseEntity<TaskDto> addTask(@Valid @RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.add(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto, @PathVariable Long id){
        taskDto.setId(id);
        return ResponseEntity.ok(taskService.update(taskDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

}
