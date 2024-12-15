package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.response.CreateCommentResponse;
import com.tugrulhan.freethoughts.dtos.response.GetCommentByArticle;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateCommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    public CreateCommentResponse createComment(CreateCommentRequest request);
    public List<GetCommentByArticle> getCommentByArticle(Long articleId);
}
