package io.amigable.wfengine.designer.repository;

import io.amigable.wfengine.designer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
