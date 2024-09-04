package org.daniel.managementspringapp.mapper;

import org.daniel.managementspringapp.dto.TaskDto;
import org.daniel.managementspringapp.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(TaskDto taskDto);

    TaskDto fromTask(Task task);
}
