package io.amigable.wfengine.designer.repository;

import org.springframework.data.repository.CrudRepository;
import io.amigable.wfengine.designer.model.ProcessDefinition;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessDefinitionRepository extends CrudRepository<ProcessDefinition, Integer>{

}
