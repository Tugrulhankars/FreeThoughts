package com.tugrulhan.freethoughts.dtos.reuqest;

import com.tugrulhan.freethoughts.enums.ArticleStatus;

import java.util.List;

public record CreateArticleRequest(
        String title,
        String content,
        String author,
        List<CreateTagRequest> tagDtos,
        ArticleStatus articleStatus
) {
}
