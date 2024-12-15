package com.tugrulhan.freethoughts.repositories;

import com.tugrulhan.freethoughts.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<com.tugrulhan.freethoughts.models.Article, Long> {

    List<Article> findByTagsId(Long id);
}
