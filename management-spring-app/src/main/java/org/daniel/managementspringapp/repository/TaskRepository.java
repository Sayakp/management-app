package org.daniel.managementspringapp.repository;

import org.daniel.managementspringapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
