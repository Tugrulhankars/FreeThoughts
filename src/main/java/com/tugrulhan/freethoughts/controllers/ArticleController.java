package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.response.CreateArticleResponse;
import com.tugrulhan.freethoughts.dtos.response.GetArticle;
import com.tugrulhan.freethoughts.dtos.response.GetArticleByTagResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateArticleRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.GetArticleByTagRequest;
import com.tugrulhan.freethoughts.services.impl.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@Tag(name = "Articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('STA_USER') or hasRole('PRO_USER')")
    public ResponseEntity<CreateArticleResponse> createArticle(@RequestBody CreateArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.createArticle(articleRequest));
    }

    @GetMapping("/getByTag")
    @Operation(summary = "Get Article By Tag", description = "Get Article By Tag")
    public ResponseEntity<List<GetArticleByTagResponse>> getArticleByTag(@RequestBody GetArticleByTagRequest request) {
        return ResponseEntity.ok(articleService.getArticleByTag(request));
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "Get Article By Id", description = "Get Article By Id")
    public ResponseEntity<GetArticle> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get All Article", description = "Get All Article")
    public ResponseEntity<List<GetArticle>> getAllArticle() {
        return ResponseEntity.ok(articleService.getAllArticle());
    }


}
