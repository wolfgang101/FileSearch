package com.foogle;

public class article {
    private String article_title;
    private String article_body;
    private String article_author;
    fileHandler file = new fileHandler(article_title);

    public article(String title, String author, String body){
        this.article_title = title;
        this.article_body = body;
        this.article_author = author;
    }

    public String getArticle_author() {
        return article_author;
    }

    public void setArticle_author(String article_author) {
        this.article_author = article_author;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_body() {
        return article_body;
    }

    public void setArticle_body(String article_body) {
        this.article_body = article_body;
    }

    public void writeArticle(){
        file.writeFile(article_body);
    }

    public String readArticle() {
        return file.readFile();
    }
}