package io.amigable.wfengine.designer.repository;

import io.amigable.wfengine.designer.model.ProcessTransition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessTransitionRepository extends JpaRepository<ProcessTransition, Integer> {

    Iterable<ProcessTransition> findByProcessDefinitionId(int processDefinitionId);
}
