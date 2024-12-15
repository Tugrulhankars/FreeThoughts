package com.tugrulhan.freethoughts.mappers;

import com.tugrulhan.freethoughts.dtos.response.GetCommentByArticle;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateCommentRequest;
import com.tugrulhan.freethoughts.models.Article;
import com.tugrulhan.freethoughts.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toComment(CreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setId(request.articleId());
        comment.setContent(request.content());
        comment.setAuthor(request.author());
        comment.setArticle(new Article());
        return comment;
    }

    public GetCommentByArticle toGetCommentByArticle(Comment comment) {
        GetCommentByArticle getCommentByArticle = new GetCommentByArticle(
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedDate()
        );

        return getCommentByArticle;
    }
}
