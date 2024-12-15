package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.response.CreateCommentResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateCommentRequest;
import com.tugrulhan.freethoughts.services.impl.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/createComment")
    @Operation(summary = "Create Comment", description = "Create Comment")
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request) {
        var result =commentService.createComment(request);
        return ResponseEntity.ok(result);
    }
}
