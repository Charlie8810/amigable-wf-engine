package io.amigable.wfengine.designer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.amigable.wfengine.designer.model.ProcessDefinition;
import io.amigable.wfengine.designer.repository.ProcessDefinitionRepository;

import io.amigable.wfengine.designer.model.ProcessInstance;
import io.amigable.wfengine.designer.repository.ProcessInstanceRepository;


@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @GetMapping(path="/all-defs")
    public @ResponseBody Iterable<ProcessDefinition> getAllProcessDefinitions(){
        return processDefinitionRepository.findAll();
    }



    @GetMapping(path="/all-instances")
    public @ResponseBody Iterable<ProcessInstance> getAllProcessInstances(){
        return processInstanceRepository.findAll();
    }
}
