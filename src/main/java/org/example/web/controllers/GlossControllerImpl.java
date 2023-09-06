package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.service.Storage;
import org.example.app.service.dao.WordImpl;
import org.example.app.service.exceptions.BookShelfLoginException;
import org.example.web.dto.GlossaryToView;
import org.example.web.dto.WordToView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "glossaries")
public class GlossControllerImpl implements GlossController {

    private Logger logger = Logger.getLogger(GlossControllerImpl.class);

    private Storage storage;


    @Autowired
    public GlossControllerImpl(Storage storage) {
        this.storage = storage;
    }


    @GetMapping("/shelf")
    public String glossaries(Model model) {
        logger.info("glossaries shelf");
        model.addAttribute("glossary", new GlossaryToView());
        model.addAttribute("glossList", storage.getAllGross());
        return "glossaries_shelf";
    }

    @PostMapping("/save")
    public String saveGlossary(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && !glossary.getRegex().isEmpty() && storage.addGlossary(glossary)) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении словаря!", "/glossaries/shelf");
        }
    }

    @PostMapping("/remove")
    public String removeGlossary(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && storage.delGlossary(glossary.getName().trim().toLowerCase())) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении словаря!","/glossaries/shelf");
        }
    }

    @PostMapping("/get")
    public String activeGloss(GlossaryToView glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && storage.setActiveByName(glossary.getName().trim().toLowerCase())) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при запросе словаря!","/glossaries/shelf");
        }
    }

    @RequestMapping("/glossary")
    public String getGlossary(Model model) {
        model.addAttribute("gloss", storage.getActiveGlossary().toString());
        model.addAttribute("word", new WordToView());
        model.addAttribute("wordsList", storage.getActiveGlossary().getGloss());
        return "glossary_words";
    }

    @PostMapping("/glossary/save")
    public String addWordValue(WordToView word) throws BookShelfLoginException {
        if (!word.getName().isEmpty() && !word.getValue().isEmpty() && storage.addWordValue(word)) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении слова!","/glossaries/glossary");
        }
    }

    @PostMapping("/glossary/remove")
    public String removeWordValue(WordToView word) throws BookShelfLoginException {
        if (!word.getName().isEmpty() && storage.delWordValue(word.getName().trim().toLowerCase())) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении слова!","/glossaries/glossary");
        }
    }

    @RequestMapping("/glossary/search")
    public String searchWord(Model model, WordToView word) throws BookShelfLoginException {
        WordImpl word1 = storage.getWordValue(word.getName());
        if (!word.getName().isEmpty() && word1 != (null) ) {
            model.addAttribute("searchWord", word.getName());
            model.addAttribute("word", new WordToView());
            model.addAttribute("wordsList", word1);
            return "search_result";
        } else {
            throw new BookShelfLoginException("Ошибка ввода либо слово не найдено!","/glossaries/glossary");
        }
    }
}
