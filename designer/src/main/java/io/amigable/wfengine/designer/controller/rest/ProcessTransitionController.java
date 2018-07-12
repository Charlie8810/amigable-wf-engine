package io.amigable.wfengine.designer.controller.rest;


import io.amigable.wfengine.designer.model.*;
import io.amigable.wfengine.designer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/api/v1/process-transition")
public class ProcessTransitionController {
    @Autowired
    private ProcessTransitionRepository processTransitionRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<ProcessTransition> getAllTransitions(){
        return processTransitionRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<ProcessTransition> findTransition(@PathVariable("id") int id){
        return processTransitionRepository.findById(id);
    }


    @GetMapping(path="/def/{definition_id}")
    public @ResponseBody Iterable<ProcessTransition> findAllByDefinition(@PathVariable("definition_id") int definition_id){
        return processTransitionRepository.findByProcessDefinitionId(definition_id);
    }




}
