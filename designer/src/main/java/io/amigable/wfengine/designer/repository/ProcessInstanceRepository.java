package io.amigable.wfengine.designer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import io.amigable.wfengine.designer.model.ProcessInstance;

public interface ProcessInstanceRepository extends PagingAndSortingRepository<ProcessInstance, Integer>{

}
