package io.amigable.wfengine.designer.repository;

import io.amigable.wfengine.designer.model.ProcessTransition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessTransitionRepository extends JpaRepository<ProcessTransition, Integer> {
}
