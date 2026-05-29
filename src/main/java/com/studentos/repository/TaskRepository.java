package com.studentos.repository;

import com.studentos.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // JpaRepository<Task, Integer>:
    // Task = the entity type this repository manages
    // Integer = the type of the primary key (id field type)

    // Spring Data Jpa provides these methods automatically - no implementation needed:
    // findAll()           → SELECT * FROM tasks
    // findById(id)        → SELECT * FROM tasks WHERE id = ?
    // save(task)          → INSERT or UPDATE (INSERT if no id, UPDATE if id exists)
    // deleteById(id)      → DELETE FROM tasks WHERE id = ?
    // count()             → SELECT COUNT(*) FROM tasks
    // existsById(id)      → SELECT EXISTS(SELECT 1 FROM tasks WHERE id = ?)

    // Custom query methods - Spring generates SQL from the method name
    // This is called "derived queries" - no SQL or @Query annotation needed
    List<Task> findByCompleted(boolean completed);
    // generates: SELECT * FROM tasks WHERE completed = ?

    List<Task> findByType(String type);
    // generates: SELECT * FROM tasks WHERE type = ?

    List<Task> findByDeadlineBefore(LocalDate date);
    // generates: SELECT * FROM tasks WHERE deadline < ?

    List<Task> findByCompletedFalseOrderByDeadlineAsc();
    // generates: SELECT * FROM tasks WHERE completed = false ORDER BY deadline ASC

    List<Task> findByTypeAndCompleted(String type, boolean completed);
    // generates: SELECT * FROM tasks WHERE type = ? AND completed = ?
}