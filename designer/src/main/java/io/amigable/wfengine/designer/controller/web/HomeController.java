package io.amigable.wfengine.designer.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
}
