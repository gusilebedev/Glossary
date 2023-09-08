package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.service.GlossaryService;
import org.example.app.service.exceptions.BookShelfLoginException;
import org.example.web.dto.GlossaryDto;
import org.example.web.dto.WordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping(value = "glossaries")
@Scope("singleton")
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
        model.addAttribute("glossary", new GlossaryDto());
        model.addAttribute("glossList", service.listAllGlossaries());
        return "glossaries_shelf";
    }

    @PostMapping("/save")
    public String saveGlossary(GlossaryDto glossary) throws BookShelfLoginException {
        if (!glossary.getName().isEmpty() && !glossary.getRegex().isEmpty() && service.saveGlossary(glossary)) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении словаря!", "/glossaries/shelf");
        }
    }

    @PostMapping("/remove")
    public String removeGlossary(GlossaryDto glossary) throws BookShelfLoginException {
        try {
            if (!glossary.getName().isEmpty()) {
                service.getGlossary(glossary.getName());
                service.deleteGlossary(glossary);
                return "redirect:/glossaries/shelf";
            } else {
                throw new BookShelfLoginException("Ошибка ввода при удалении словаря!", "/glossaries/shelf");
            }
        } catch (NoSuchElementException exception) {
            throw new BookShelfLoginException("Cловарь не найден!", "/glossaries/shelf");
        }
    }


    @GetMapping("/glossary")
    public String getGlossary(@RequestParam String name, Model model) throws BookShelfLoginException {
        try {
            if (!name.isEmpty()) {
                logger.info("glossaries glossary");
                model.addAttribute("gloss", service.getGlossary(name));
                model.addAttribute("word", new WordDto());
                model.addAttribute("wordsList", service.listAllWords());
                return "glossary_words";
            } else {
                throw new BookShelfLoginException("Ошибка ввода при запросе словаря!", "/glossaries/shelf");
            }
        } catch (NoSuchElementException exception) {
            throw new BookShelfLoginException("Cловарь не найден!", "/glossaries/shelf");
        }
    }

    @PostMapping("/glossary/save")
    public String addWordValue(WordDto word, Model model) throws BookShelfLoginException {
        if (!word.getName().isEmpty() && !word.getValue().isEmpty()) {
            service.saveWord(word);
            model.addAttribute("gloss", service.getGlossary(word.getGlossary()));
            model.addAttribute("word", new WordDto());
            model.addAttribute("wordsList", service.listAllWords());
            return "glossary_words";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении слова!", "/glossaries/glossary", word.getGlossary());
        }
    }

    @PostMapping("/glossary/remove")
    public String removeWordValue(WordDto word, Model model) throws BookShelfLoginException {
        try {
            if (!word.getName().isEmpty()) {
                service.getWord(word);
                service.deleteWord(word);
                model.addAttribute("gloss", service.getGlossary(word.getGlossary()));
                model.addAttribute("word", new WordDto());
                model.addAttribute("wordsList", service.listAllWords());
                return "glossary_words";
            } else {
                throw new BookShelfLoginException("Введите слово для поиска!", "/glossaries/glossary", word.getGlossary());
            }
        } catch (NoSuchElementException exception) {
            throw new BookShelfLoginException("Cлово не найдено!", "/glossaries/glossary", word.getGlossary());
        }
    }

    @GetMapping("/glossary/search")
    public String searchWord(Model model, WordDto word) throws BookShelfLoginException {
        try {
            if (!word.getName().isEmpty()) {
                logger.info("glossaries glossary search");
                model.addAttribute("glossary", new GlossaryDto());
                model.addAttribute("glossName", word.getGlossary());
                model.addAttribute("searchWord", word.getName());
                model.addAttribute("word", service.getWord(word));
                return "search_result";
            } else {
                throw new BookShelfLoginException("Введите слово для поиска!", "/glossaries/glossary", word.getGlossary());
            }
        } catch (NoSuchElementException exception) {
            throw new BookShelfLoginException("Cлово не найдено!", "/glossaries/glossary", word.getGlossary());
        }
    }
}
