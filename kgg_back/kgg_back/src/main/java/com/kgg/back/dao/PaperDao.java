package com.kgg.back.dao;

import com.kgg.back.pojo.Paper;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaperDao extends Neo4jRepository<Paper,Long>{
    @Query("MATCH (n:Author {authorID: $authorID})-[:WRITE]->(p:Paper) RETURN p")
    List<Paper> findwritePaper(@Param("authorID") String authorID);

    @Query("MATCH (n:Paper) WHERE n.title=~\"(?i).*\"+$paperkeys+\".*\" or n.summary=~\"(?i).*\"+$paperkeys+\".*\" RETURN n")
    List<Paper> findPaper(@Param("paperkeys") String paperkeys);

    @Query("MATCH (n:Paper) WHERE n.title=$title RETURN n")
    Paper findPaperbyID(@Param("title") String title);

    @Query("MATCH (n:Paper)-[r:CITE]->(t:Paper) WHERE n.paperID=$paperID RETURN t")
    List<Paper> findcitePaper(@Param("paperID") String paperID);

    @Query("MATCH (n:Paper)<-[r:CITE]-(t:Paper) WHERE n.paperID=$paperID RETURN t")
    List<Paper> findcitedPaper(@Param("paperID") String paperID);

}
