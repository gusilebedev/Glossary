package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.service.Storage;
import org.example.app.service.exceptions.BookShelfLoginException;
import org.example.web.dto.GlossaryImpl;
import org.example.web.dto.Word;
import org.example.web.dto.WordImpl;
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
        model.addAttribute("glossary", new GlossaryImpl());
        model.addAttribute("glossList", storage.getAllGross());
        // faefaea
        return "glossaries_shelf";
    }

    @PostMapping("/save")
    public String saveGlossary(Model model, GlossaryImpl glossary) throws BookShelfLoginException {
        if (!glossary.getNameGloss().isEmpty() && !glossary.getRegex().isEmpty() && storage.addGlossary(glossary)) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении словаря!", "/glossaries/shelf");
        }
    }

    @PostMapping("/remove")
    public String removeGlossary(@RequestParam String glossaryNameToRemove) throws BookShelfLoginException {
        if (!glossaryNameToRemove.isEmpty() && storage.delGlossary(glossaryNameToRemove.trim().toLowerCase())) {
            return "redirect:/glossaries/shelf";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении словаря!","/glossaries/shelf");
        }
    }

    @PostMapping("/get")
    public String activeGloss(@RequestParam String glossaryName) throws BookShelfLoginException {
        if (!glossaryName.isEmpty() && storage.setActiveByName(glossaryName.trim().toLowerCase())) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при запросе словаря!","/glossaries/shelf");
        }
    }

    @RequestMapping("/glossary")
    public String getGlossary(Model model) {
        model.addAttribute("gloss", storage.getActiveGlossary().toString());
        model.addAttribute("word", new WordImpl());
        model.addAttribute("wordsList", storage.getActiveGlossary().getGloss());
        return "glossary_words";
    }

    @PostMapping("/glossary/save")
    public String addWordValue(@RequestParam String wordName, @RequestParam String wordValue) throws BookShelfLoginException {
        String word = wordName + " " + wordValue;
        if (storage.addWordValue(word.trim().toLowerCase())) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при добавлении слова!","/glossaries/glossary");
        }
    }

    @PostMapping("/glossary/remove")
    public String removeWordValue(@RequestParam String wordNameToRemove) throws BookShelfLoginException {
        if (!wordNameToRemove.isEmpty() && storage.delWordValue(wordNameToRemove.trim().toLowerCase())) {
            return "redirect:/glossaries/glossary";
        } else {
            throw new BookShelfLoginException("Ошибка ввода при удалении слова!","/glossaries/glossary");
        }
    }

    @RequestMapping("/glossary/search")
    public String searchWord(Model model, @RequestParam String wordNameToSearch) throws BookShelfLoginException {
        Word word = storage.getWordValue(wordNameToSearch);
        if (!wordNameToSearch.isEmpty() && word != (null) ) {
            model.addAttribute("searchWord", wordNameToSearch);
            model.addAttribute("word", new WordImpl());
            model.addAttribute("wordsList", word);
            return "search_result";
        } else {
            throw new BookShelfLoginException("Ошибка ввода либо слово не найдено!","/glossaries/glossary");
        }
    }
}
