package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.response.CreateArticleResponse;
import com.tugrulhan.freethoughts.dtos.response.GetArticle;
import com.tugrulhan.freethoughts.dtos.response.GetArticleByTagResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateArticleRequest;
import com.tugrulhan.freethoughts.dtos.reuqest.GetArticleByTagRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    public CreateArticleResponse createArticle(CreateArticleRequest articleRequest);
    public List<GetArticleByTagResponse>  getArticleByTag(GetArticleByTagRequest request);
    public GetArticle getArticleById(Long id);
    public List<GetArticle> getAllArticle();
}
