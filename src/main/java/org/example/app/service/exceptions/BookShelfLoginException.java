package org.example.app.service.exceptions;

import lombok.Getter;
import org.example.web.dto.WordDto;

@Getter
public class BookShelfLoginException extends Exception {
    private final String message;

    private final String url;

    private String glossary;

    public BookShelfLoginException(String message, String url, String glossary) {
        this.message = message;
        this.url = url;
        this.glossary = glossary;
    }

    public BookShelfLoginException(String message, String url) {
        this.message = message;
        this.url = url;
    }
}

