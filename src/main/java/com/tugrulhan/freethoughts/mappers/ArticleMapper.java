package com.tugrulhan.freethoughts.mappers;

import com.tugrulhan.freethoughts.dtos.response.GetArticle;
import com.tugrulhan.freethoughts.dtos.response.GetCommentByArticle;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateArticleRequest;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.models.Comment;
import com.tugrulhan.freethoughts.models.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public Article toArticle(CreateArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.title());
        article.setContent(articleRequest.content());
        article.setAuthor(articleRequest.author());
        article.setArticleStatus(articleRequest.articleStatus());
        article.setTags(articleRequest.tagDtos()
                .stream()
                .map(tagDto -> new Tag(tagDto.tagName()))
                .collect(Collectors.toList()));

        return article;
    }

    public GetArticle toGetArticle(Article article, List<GetCommentByArticle> comment) {
        GetArticle getArticle = new GetArticle(
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getLikes().stream().count(),
                comment
        );

        return getArticle;
    }

}
