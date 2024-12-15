package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.dtos.reuqest.AddLikeRequest;
import com.tugrulhan.freethoughts.mappers.LikeMapper;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.models.Like;
import com.tugrulhan.freethoughts.repositories.ArticleRepository;
import com.tugrulhan.freethoughts.repositories.LikeRepository;
import com.tugrulhan.freethoughts.services.impl.ArticleService;
import com.tugrulhan.freethoughts.services.impl.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final ArticleRepository articleRepository;


    public LikeServiceImpl(LikeRepository likeRepository, LikeMapper likeMapper, ArticleRepository articleRepository) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public void addLike(AddLikeRequest request) {
        Article article=articleRepository.findById(request.articleId()).get();
        Like like=likeMapper.toLike(request,article);
        likeRepository.save(like);
    }
}
