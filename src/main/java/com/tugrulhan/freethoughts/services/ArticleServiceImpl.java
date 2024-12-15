package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.dtos.response.CreateArticleResponse;
import com.tugrulhan.freethoughts.dtos.response.GetArticle;
import com.tugrulhan.freethoughts.dtos.response.GetArticleByTagResponse;
import com.tugrulhan.freethoughts.dtos.response.GetCommentByArticle;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateArticleRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.GetArticleByTagRequest;
import com.tugrulhan.freethoughts.mappers.ArticleMapper;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.repositories.ArticleRepository;
import com.tugrulhan.freethoughts.services.impl.ArticleService;
import com.tugrulhan.freethoughts.services.impl.CommentService;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentService commentService;
    private final Tracer tracer;
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper, CommentService commentService, Tracer tracer) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.commentService = commentService;
        this.tracer = tracer;
    }

    @Override
    public CreateArticleResponse createArticle(CreateArticleRequest articleRequest) {
        Span span=tracer.spanBuilder("createArticle").startSpan();
        articleRepository.save(articleMapper.toArticle(articleRequest));
        span.addEvent("Article created ");
        span.end();
        return new CreateArticleResponse("Article created successfully");
    }

    @Cacheable(value = "articlesByTag", key = "#request.id()")
    @Override
    public List<GetArticleByTagResponse> getArticleByTag(GetArticleByTagRequest request) {
            Span span=tracer.spanBuilder("getArticleByTag").startSpan();
            List<Article> articles = articleRepository.findByTagsId(request.id());
            span.addEvent("Articles fetched by tag");
            List<GetArticleByTagResponse> byTagResponses=new ArrayList<>();
            for (Article article : articles) {
                GetArticleByTagResponse getArticleByTagResponse=new GetArticleByTagResponse(
                        article.getTitle(),
                        article.getContent(),
                        article.getLikes().stream().count(),
                        article.getComments().toString()

                );
                byTagResponses.add(getArticleByTagResponse);
            }
            span.end();
            return byTagResponses;
    }

    @Override
    public GetArticle getArticleById(Long id) {
        Span span=tracer.spanBuilder("getArticleById").startSpan();
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        span.addEvent("Article fetched by id");
        List<GetCommentByArticle> comments = commentService.getCommentByArticle(article.getId());
        span.addEvent("Comments fetched by article id");GetArticle getArticle = articleMapper.toGetArticle(article, comments);
        span.end();
        return getArticle;
    }

    @Override
    public List<GetArticle> getAllArticle() {
        Span span=tracer.spanBuilder("getAllArticle").startSpan();
        List<Article> articles = articleRepository.findAll();
        span.addEvent("Articles fetched");
        List<GetArticle> getArticles = new ArrayList<>();
        for (Article article : articles) {
           GetArticle getArticle = articleMapper.toGetArticle(article, commentService.getCommentByArticle(article.getId()));
           getArticles.add(getArticle);
        }
        span.end();
        return getArticles;
    }


    public void deleteArticle(Long id) {
        Span span=tracer.spanBuilder("deleteArticle").startSpan();
        articleRepository.deleteById(id);
        span.addEvent("Article deleted");
        span.end();
    }
}
