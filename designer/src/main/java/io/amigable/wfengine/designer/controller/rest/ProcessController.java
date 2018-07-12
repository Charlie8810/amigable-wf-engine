package io.amigable.wfengine.designer.controller.rest;

import io.amigable.wfengine.designer.model.*;
import io.amigable.wfengine.designer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/api/v1/process")
public class ProcessController {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;


    @GetMapping(path="/all")
    public @ResponseBody Iterable<ProcessDefinition> getAllProcessDefinitions(){
        return processDefinitionRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<ProcessDefinition> findProcessDefinitions(@PathVariable("id") int id){
        return processDefinitionRepository.findById(id);
    }



}
