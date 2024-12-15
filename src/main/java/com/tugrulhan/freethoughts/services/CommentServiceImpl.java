package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.dtos.response.CreateCommentResponse;
import com.tugrulhan.freethoughts.dtos.response.GetCommentByArticle;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateCommentRequest;
import com.tugrulhan.freethoughts.mappers.CommentMapper;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.models.Comment;
import com.tugrulhan.freethoughts.repositories.ArticleRepository;
import com.tugrulhan.freethoughts.repositories.CommentRepository;
import com.tugrulhan.freethoughts.services.impl.CommentService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private  final ArticleRepository articleRepository;
    private final Tracer tracer;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, ArticleRepository articleRepository, Tracer tracer) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.articleRepository = articleRepository;
        this.tracer = tracer;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        Span span=tracer.spanBuilder("createComment").startSpan();
        try {
            CreateCommentResponse createCommentResponse = new CreateCommentResponse(request.content());

            Article article = articleRepository.findById(request.articleId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));
            span.addEvent("Article fetched by id");
            Comment comment = new Comment();
            comment.setContent(request.content());
            comment.setAuthor(request.author());
            comment.setArticle(article);
            comment.setCreatedDate(LocalDateTime.now());
            comment.setUpdatedDate(LocalDateTime.now());

            commentRepository.save(comment);
            span.addEvent("Comment saved");
            span.end();
            return createCommentResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error creating comment", e);
        }
    }

    @Override
    public List<GetCommentByArticle> getCommentByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<GetCommentByArticle> getCommentByArticles = new ArrayList<>(); // Boş bir ArrayList oluşturun
        for (Comment comment1 : comments) {
            GetCommentByArticle getCommentByArticle = commentMapper.toGetCommentByArticle(comment1);
            getCommentByArticles.add(getCommentByArticle); // Eleman eklenebilir
        }
        return getCommentByArticles; // Listeyi döndürüyoruz

    }

}
