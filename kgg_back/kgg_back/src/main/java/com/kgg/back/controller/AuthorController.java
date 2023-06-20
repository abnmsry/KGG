package com.kgg.back.controller;

import com.kgg.back.pojo.Author;
import com.kgg.back.pojo.CustomNode;
import com.kgg.back.pojo.Paper;
import com.kgg.back.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AuthorController {
    @Autowired
    Service service;
    /////paper
    @GetMapping("/paper/paperkey/{paperkeys}")
    public List<CustomNode> findPaper(@PathVariable("paperkeys") String paperkeys){
        List<Paper> paperSet=service.findPaper(paperkeys);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Paper paper: paperSet){
            List<Map<String, Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("title",paper.getTitle());
            map.put("author",paper.getAuthor());
            map.put("address",paper.getAddress());
            map.put("summary",paper.getSummary());
            map.put("vicuna_summary",paper.getVicunaSummary());
            map.put("booktitle",paper.getBooktitle());
            map.put("journal",paper.getJournal());
            map.put("volume",paper.getVolume());
            map.put("pages",paper.getPages());
            map.put("number",paper.getNumber());
            map.put("year",paper.getYear());
            map.put("month",paper.getMonth());
            map.put("publisher",paper.getPublisher());
            map.put("editor",paper.getEditor());
            map.put("language",paper.getLanguage());
            map.put("doi",paper.getDoi());
            map.put("url",paper.getUrl());
            map.put("ISBN",paper.getISBN());
            map.put("note",paper.getNote());
            map.put("paperID",paper.getPaperID());
            map.put("query_id",paper.getQuery_id());
            prop.add(map);
            customNodes.add(new CustomNode(paper.getPaperID(),prop,1));
        }
        return customNodes;
    }


    @GetMapping("/paper/papertitle/{title}")
    public CustomNode findPaperbyID(@PathVariable("title") String title){
        Paper paper=service.findPaperbyID(title);
        List<Map<String, Object>> prop = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("title",paper.getTitle());
        map.put("author",paper.getAuthor());
        map.put("address",paper.getAddress());
        map.put("summary",paper.getSummary());
        map.put("vicuna_summary",paper.getVicunaSummary());
        map.put("booktitle",paper.getBooktitle());
        map.put("journal",paper.getJournal());
        map.put("volume",paper.getVolume());
        map.put("pages",paper.getPages());
        map.put("number",paper.getNumber());
        map.put("year",paper.getYear());
        map.put("month",paper.getMonth());
        map.put("publisher",paper.getPublisher());
        map.put("editor",paper.getEditor());
        map.put("language",paper.getLanguage());
        map.put("doi",paper.getDoi());
        map.put("url",paper.getUrl());
        map.put("ISBN",paper.getISBN());
        map.put("note",paper.getNote());
        map.put("paperID",paper.getPaperID());
        map.put("query_id",paper.getQuery_id());
        prop.add(map);
        return new CustomNode(paper.getPaperID(),prop,1);
    }

    @GetMapping("/paper/paperIDcitation/{paperID}")
    public List<CustomNode> findcitePaper(@PathVariable("paperID") String paperID){
        List<Paper> paperSet=service.findcitePaper(paperID);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Paper paper: paperSet){
            List<Map<String, Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("title",paper.getTitle());
            map.put("author",paper.getAuthor());
            map.put("address",paper.getAddress());
            map.put("summary",paper.getSummary());
            map.put("vicuna_summary",paper.getVicunaSummary());
            map.put("booktitle",paper.getBooktitle());
            map.put("journal",paper.getJournal());
            map.put("volume",paper.getVolume());
            map.put("pages",paper.getPages());
            map.put("number",paper.getNumber());
            map.put("year",paper.getYear());
            map.put("month",paper.getMonth());
            map.put("publisher",paper.getPublisher());
            map.put("editor",paper.getEditor());
            map.put("language",paper.getLanguage());
            map.put("doi",paper.getDoi());
            map.put("url",paper.getUrl());
            map.put("ISBN",paper.getISBN());
            map.put("note",paper.getNote());
            map.put("paperID",paper.getPaperID());
            map.put("query_id",paper.getQuery_id());
            prop.add(map);
            customNodes.add(new CustomNode(paper.getPaperID(),prop,1));
        }
        return customNodes;
    }

    @GetMapping("/paper/paperIDcited/{paperID}")
    public List<CustomNode> findcitedPaper(@PathVariable("paperID") String paperID){
        List<Paper> paperSet=service.findcitedPaper(paperID);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Paper paper: paperSet){
            List<Map<String, Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("title",paper.getTitle());
            map.put("author",paper.getAuthor());
            map.put("address",paper.getAddress());
            map.put("summary",paper.getSummary());
            map.put("vicuna_summary",paper.getVicunaSummary());
            map.put("booktitle",paper.getBooktitle());
            map.put("journal",paper.getJournal());
            map.put("volume",paper.getVolume());
            map.put("pages",paper.getPages());
            map.put("number",paper.getNumber());
            map.put("year",paper.getYear());
            map.put("month",paper.getMonth());
            map.put("publisher",paper.getPublisher());
            map.put("editor",paper.getEditor());
            map.put("language",paper.getLanguage());
            map.put("doi",paper.getDoi());
            map.put("url",paper.getUrl());
            map.put("ISBN",paper.getISBN());
            map.put("note",paper.getNote());
            map.put("paperID",paper.getPaperID());
            map.put("query_id",paper.getQuery_id());
            prop.add(map);
            customNodes.add(new CustomNode(paper.getPaperID(),prop,1));
        }
        return customNodes;
    }

    @GetMapping("/paper/paperIDauthor/{paperID}")
    public List<CustomNode> findwriteAuthor(@PathVariable("paperID") String paperID){
        List<Author> authorSet=service.findwriteAuthor(paperID);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Author author: authorSet){
            List<Map<String,Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("authorID",author.getAuthorID());
            map.put("authorName",author.getAuthorName());
            map.put("h_index",author.getH_index());
            map.put("affiliations",author.getAffiliations());
            prop.add(map);
            customNodes.add(new CustomNode(author.getAuthorID(),prop,2));
        }
        return customNodes;
    }


    ////author
    //test for one sample
    @GetMapping("/author/authorkey/{authorkeys}")
    public List<CustomNode> findAuthor(@PathVariable("authorkeys") String authorkeys){
        List<Author> authorSet=service.findAuthor(authorkeys);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Author author: authorSet){
            List<Map<String,Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("authorID",author.getAuthorID());
            map.put("authorName",author.getAuthorName());
            map.put("h_index",author.getH_index());
            map.put("affiliations",author.getAffiliations());
            prop.add(map);
            customNodes.add(new CustomNode(author.getAuthorID(),prop,2));
        }
        return customNodes;
    }

    @GetMapping("/author/authorIDpaper/{authorID}")
    public List<CustomNode> findwritePaper(@PathVariable("authorID") String authorID){
        List<Paper> paperSet=service.findwritePaper(authorID);
        List<CustomNode> customNodes = new ArrayList<>();
        //逐步把获取到的paper加入新的node里
        for (Paper paper: paperSet){
            List<Map<String, Object>> prop = new ArrayList<>();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("title",paper.getTitle());
            map.put("author",paper.getAuthor());
            map.put("address",paper.getAddress());
            map.put("summary",paper.getSummary());
            map.put("vicuna_summary",paper.getVicunaSummary());
            map.put("booktitle",paper.getBooktitle());
            map.put("journal",paper.getJournal());
            map.put("volume",paper.getVolume());
            map.put("pages",paper.getPages());
            map.put("number",paper.getNumber());
            map.put("year",paper.getYear());
            map.put("month",paper.getMonth());
            map.put("publisher",paper.getPublisher());
            map.put("editor",paper.getEditor());
            map.put("language",paper.getLanguage());
            map.put("doi",paper.getDoi());
            map.put("url",paper.getUrl());
            map.put("ISBN",paper.getISBN());
            map.put("note",paper.getNote());
            map.put("paperID",paper.getPaperID());
            map.put("query_id",paper.getQuery_id());
            prop.add(map);
            customNodes.add(new CustomNode(paper.getPaperID(),prop,1));
        }
        return customNodes;
    }

    //for all samples
//    @GetMapping("/all")
//    public List<CustomNode> findAll(){
//        Iterable<Author> authorIterable = authorService.findAll();
//        List<CustomNode> customNodes = new ArrayList<>();
//        for (Author author : authorIterable){
////            System.out.println(author.getAuthorName());
//            customNodes.add(new CustomNode(author.getAuthorName(),prop,1));
//        }
//        return customNodes;
//    }

}

