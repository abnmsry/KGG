package com.kgg.back.pojo;

import org.springframework.data.neo4j.core.schema.*;

import org.springframework.data.neo4j.core.schema.TargetNode;

import java.sql.Array;
import java.util.*;

@Node
public class Paper {
    @Id
    @GeneratedValue
    private Long id;
    private String ISBN;
    private String address;
    private String summary;

    private String vicunaSummary;
    private List<String> author;
    private String booktitle;
    private String doi;
    private String editor;
//    private String entry_id;
    private String journal;
    private String language;
    private String month;
    private String note;
    private String number;
    private String pages;
    private String paperID;
    private String publisher;
    private String query_id;
    private String title;
    private String url;
    private String volume;
    private String year;


//    @Relationship(type = "WRITE",direction = Relationship.Direction.INCOMING)
//    private List<Author> writeAuthor;
//
//    @Relationship(type = "CITE",direction=Relationship.Direction.OUTGOING)
//    private List<Paper> citePaper;


    private Paper(){

    }

    public Paper(Long id, String ISBN, String address, String summary, String vicunaSummary, List<String> author, String booktitle, String doi, String editor, String journal, String language, String month, String note, String number, String pages, String paperID, String publisher, String query_id, String title, String url, String volume, String year) {
        this.id = id;
        this.ISBN = ISBN;
        this.address = address;
        this.summary = summary;
        this.vicunaSummary = vicunaSummary;
        this.author = author;
        this.booktitle = booktitle;
        this.doi = doi;
        this.editor = editor;
        this.journal = journal;
        this.language = language;
        this.month = month;
        this.note = note;
        this.number = number;
        this.pages = pages;
        this.paperID = paperID;
        this.publisher = publisher;
        this.query_id = query_id;
        this.title = title;
        this.url = url;
        this.volume = volume;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getVicunaSummary() {
        return vicunaSummary;
    }

    public void setVicunaSummary(String vicunaSummary) {
        this.vicunaSummary = vicunaSummary;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPaperID() {
        return paperID;
    }

    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
