package com.tugrulhan.freethoughts.mappers;

import com.tugrulhan.freethoughts.dtos.reuqest.AddLikeRequest;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.models.Like;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeMapper {

    public Like toLike(AddLikeRequest request, Article article) {
        Like like = new Like();
        like.setArticle_id(article.getId());
        like.setCreatedDate(LocalDateTime.now());
        like.setArticle(article);
        return like;
    }
}
