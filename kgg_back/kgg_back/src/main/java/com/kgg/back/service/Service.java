package com.kgg.back.service;


import com.kgg.back.dao.AuthorDao;
import com.kgg.back.dao.PaperDao;
import com.kgg.back.pojo.Author;
import com.kgg.back.pojo.Paper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    PaperDao paperDao;

    //test
    public List<Author> findAuthor(String authorkeys){
        return authorDao.findAuthor(authorkeys);
    }

    public List<Author> findwriteAuthor(String paperID){
        return authorDao.findwriteAuthor(paperID);
    }

    //find paper wrote by authors
    public List<Paper> findwritePaper(String authorID){
        return paperDao.findwritePaper(authorID);
    }

    //find paper containing keys
    public List<Paper> findPaper(String paperkeys){
        return paperDao.findPaper(paperkeys);
    }

    public Paper findPaperbyID(String title) {return paperDao.findPaperbyID(title);}

    public List<Paper> findcitePaper(String paperID){
        return paperDao.findcitePaper(paperID);
    }

    public List<Paper> findcitedPaper(String paperID){
        return paperDao.findcitedPaper(paperID);
    }


    //for all samples
//    public Iterable<Author> findAll(){
//
//        return authorDao.findAll();
//    }

}
