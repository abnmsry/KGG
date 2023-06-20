package com.kgg.back.pojo;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    //定义属性
    private String authorID;
    private String authorName;
    private String author_name_aliases;
    private Integer h_index;
    private List<String> affiliations;

//    @Relationship(type = "WRITE",direction= Relationship.Direction.OUTGOING)
//    private Set<Paper> writePaper = new HashSet<>();


    private Author(){
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorID='" + authorID + '\'' +
                ", authorName='" + authorName + '\'' +
                ", author_name_aliases='" + author_name_aliases + '\'' +
                ", h_index=" + h_index +
                ", affiliations=" + affiliations +
                '}';
    }
// generate from constructor


    public Author(Long id, String authorID, String authorName, String author_name_aliases, Integer h_index, List<String> affiliations) {
        this.id = id;
        this.authorID = authorID;
        this.authorName = authorName;
        this.author_name_aliases = author_name_aliases;
        this.h_index = h_index;
        this.affiliations = affiliations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthor_name_aliases() {
        return author_name_aliases;
    }

    public void setAuthor_name_aliases(String author_name_aliases) {
        this.author_name_aliases = author_name_aliases;
    }

    public Integer getH_index() {
        return h_index;
    }

    public void setH_index(Integer h_index) {
        this.h_index = h_index;
    }

    public List<String> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<String> affiliations) {
        this.affiliations = affiliations;
    }

}
