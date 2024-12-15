package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.reuqest.AddLikeRequest;
import com.tugrulhan.freethoughts.services.impl.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
@Tag(name = "Like", description = "Like")
public class LikeControllers {

    private final LikeService likeService;

    public LikeControllers(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/addLike")
    @Operation(summary = "Add Like", description = "Add Like")
    public void addLike(@RequestBody  AddLikeRequest request){
        likeService.addLike(request);
    }
}
