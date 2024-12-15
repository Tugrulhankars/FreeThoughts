package com.tugrulhan.freethoughts.controllers;

import com.tugrulhan.freethoughts.dtos.reuqest.CreateTagRequest;
import com.tugrulhan.freethoughts.services.impl.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@Tag(name = "Tag", description = "Tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/createTag")
    public ResponseEntity createTag(@RequestBody List<CreateTagRequest> request) {
        tagService.createTag(request);
        return ResponseEntity.ok().build();
    }
}
