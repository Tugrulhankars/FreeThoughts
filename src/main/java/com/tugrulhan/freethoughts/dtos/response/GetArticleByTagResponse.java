package com.tugrulhan.freethoughts.dtos.response;

public record GetArticleByTagResponse(
    String title,
    String content,
    Long likeCount,
    String comment
) {
}
