package io.amigable.wfengine.designer.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/wf-designer")
public class HomeController {

    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/inicio")
    public String main() {
        return "main";
    }

    @RequestMapping(value="/procesos")
    public String process() {
        return "process";
    }


    @RequestMapping(value="/procesos/{id}")
    public String processDetail(@PathVariable("id") int id) {
        return "processDetail";
    }

}
