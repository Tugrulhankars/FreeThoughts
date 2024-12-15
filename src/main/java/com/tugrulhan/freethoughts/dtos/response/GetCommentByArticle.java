package com.tugrulhan.freethoughts.dtos.response;

import java.time.LocalDateTime;

public record GetCommentByArticle(
        String content,
        String author,
        LocalDateTime createdDate
) {
}
