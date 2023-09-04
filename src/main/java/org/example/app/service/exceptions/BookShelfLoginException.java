package org.example.app.service.exceptions;

public class BookShelfLoginException extends Exception {
    private final String message;

    private final String url;

    public BookShelfLoginException(String message, String url) {
        this.message = message;
        this.url = url;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}

