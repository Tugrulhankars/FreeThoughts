package com.tugrulhan.freethoughts.dtos.response;

import java.util.List;

public record GetArticle(
        String title,
        String content,
        String author,
        Long likeCount,
        List<GetCommentByArticle> comments
) {
}
