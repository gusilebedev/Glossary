package org.example.web.controllers;

import jakarta.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.example.app.service.GlossaryService;
import org.example.app.service.dao.Word;
import org.example.app.service.exceptions.BookShelfLoginException;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping(value = "glossaries")
public class GlossControllerImpl implements GlossController {

    private Logger logger = Logger.getLogger(GlossControllerImpl.class);

    private GlossaryService service;


    @Autowired
    public GlossControllerImpl(GlossaryService service) {
        this.service = service;
    }


    @GetMapping("/shelf")
    public String glossaries(Model model) {
        logger.info("glossaries shelf");
        model.addAttribute("glossary", new GlossaryToView());
        model.addAttribute("glossList", service.listAllGlossaries());
        return "glossaries_shelf";
    }

    @PostMapping("/save")
    public String saveGlossary(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && !glossary.getRegex().isEmpty() && service.saveGlossary(glossary)) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении словаря!", "/glossaries/shelf");
        }
    }

    @PostMapping("/remove")
    public String removeGlossary(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && service.deleteGlossary(glossary)) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении словаря!","/glossaries/shelf");
        }
    }

    @PostMapping("/get")
    public String activeGloss(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && service.setActiveByName(glossary)) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при запросе словаря!","/glossaries/shelf");
        }
    }

    @RequestMapping("/glossary")
    public String getGlossary(Model model) {
        model.addAttribute("gloss", service.getActiveGlossary().toString());
        model.addAttribute("word", new WordToView());
        model.addAttribute("wordsList", service.listAllWords());
        return "glossary_words";
    }

    @PostMapping("/glossary/save")
    public String addWordValue(WordToView word) throws BookShelfLoginException {
        if (!word.getName().isEmpty() && !word.getValue().isEmpty() && service.saveWord(word)) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении слова!","/glossaries/glossary");
        }
    }

    @PostMapping("/glossary/remove")
    public String removeWordValue(WordToView word) throws BookShelfLoginException {
        if (!word.getName().isEmpty() && service.deleteWord(word)) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении слова!","/glossaries/glossary");
        }
    }

    @RequestMapping("/glossary/search")
    public String searchWord(Model model, WordToView word) throws BookShelfLoginException {
        try {
            if (!word.getName().isEmpty()) {
                model.addAttribute("searchWord", word.getName());
                model.addAttribute("word", new WordToView());
                model.addAttribute("wordsList", service.getWord(word));
                return "search_result";
            } else {
                throw new BookShelfLoginException("Введите слово для поиска!","/glossaries/glossary");
            }
        } catch (NoSuchElementException exception) {
            throw new BookShelfLoginException("Cлово не найдено!","/glossaries/glossary");
        }
    }
}
