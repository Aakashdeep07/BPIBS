package com.aakash.bpibs.ModelClass;

public class QuestionPaperHandler {
    public String paperTitle;
    public String paperDescription;
    String paperLink;

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperDescription() {
        return paperDescription;
    }

    public void setPaperDescription(String paperDescription) {
        this.paperDescription = paperDescription;
    }

    public String getPaperLink() {
        return paperLink;
    }

    public void setPaperLink(String paperLink) {
        this.paperLink = paperLink;
    }

    public QuestionPaperHandler() {
    }

    public QuestionPaperHandler(String paperTitle, String paperDescription, String paperLink) {
        this.paperTitle = paperTitle;
        this.paperDescription = paperDescription;
        this.paperLink = paperLink;
    }
}
