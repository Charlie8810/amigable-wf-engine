package io.amigable.wfengine.designer.controller.rest;

import io.amigable.wfengine.designer.model.*;
import io.amigable.wfengine.designer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/fake-api/v1")
public class MainController {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProcessTransitionRepository processTransitionRepository;

    @Autowired
    private ProcessTransitionConditionRepository processTransitionConditionRepository;


    @GetMapping(path="/all-defs")
    public @ResponseBody Iterable<ProcessDefinition> getAllProcessDefinitions(){
        return processDefinitionRepository.findAll();
    }

    @GetMapping(path="/all-instances")
    public @ResponseBody Iterable<ProcessInstance> getAllProcessInstances(){
        return processInstanceRepository.findAll();
    }

    @GetMapping(path="/all-tasks")
    public @ResponseBody Iterable<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @GetMapping(path="/all-transitions")
    public @ResponseBody Iterable<ProcessTransition> getAllTransitions(){
        return processTransitionRepository.findAll();
    }

    @GetMapping(path="/all-transition-conds")
    public @ResponseBody Iterable<ProcessTransitionCondition> getAllTransitionConds(){
        return processTransitionConditionRepository.findAll();
    }

}
