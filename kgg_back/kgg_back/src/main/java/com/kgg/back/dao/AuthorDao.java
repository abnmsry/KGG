package com.kgg.back.dao;

import com.kgg.back.pojo.Author;
import com.kgg.back.pojo.Paper;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

//数据库查询的接口
public interface AuthorDao extends Neo4jRepository<Author, Long> {
    @Query("MATCH (n:Author) WHERE n.authorName=~\"(?i).*\"+$authorkeys+\".*\" OR ANY(nn IN n.name_aliases WHERE nn=~\"(?i).*\"+$authorkeys+\".*\") RETURN n")
    List<Author> findAuthor(@Param("authorkeys") String authorkeys);

    @Query("MATCH (n:Author)-[:WRITE]->(p:Paper {paperID: $paperID}) RETURN n")
    List<Author> findwriteAuthor(@Param("paperID") String paperID);


}
