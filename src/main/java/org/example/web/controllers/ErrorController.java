package org.example.web.controllers;

import org.example.app.service.exceptions.BookShelfLoginException;
import org.example.web.dto.GlossaryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String notFoundError(Model model) {
        model.addAttribute("errorMassage", "404 Not Found");
        model.addAttribute("urlToBack", "/glossaries/shelf");
        return "error";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handError(Model model, BookShelfLoginException exception) {
        model.addAttribute("glossary", new GlossaryDto());
        model.addAttribute("errorMassage", exception.getMessage());
        model.addAttribute("urlToBack", exception.getUrl());
        model.addAttribute("glossName",exception.getGlossary());
        return "error";
    }

    
}
