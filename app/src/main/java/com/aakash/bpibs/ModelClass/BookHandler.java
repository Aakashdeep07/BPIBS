package com.aakash.bpibs.ModelClass;


public class BookHandler {
    public String bookTitle;
    public String bookDescription;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public BookHandler() {
    }

    public BookHandler(String bookTitle, String bookDescription, String bookLink) {
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookLink = bookLink;
    }

    String bookLink;

}

