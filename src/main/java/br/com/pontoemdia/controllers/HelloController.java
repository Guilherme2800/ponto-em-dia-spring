package br.com.pontoemdia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/")
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
}