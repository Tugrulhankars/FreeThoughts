package com.tugrulhan.freethoughts.dtos.reuqest;

public record CreateCommentRequest(
        String content,
        String author,
        Long articleId
) {
}
