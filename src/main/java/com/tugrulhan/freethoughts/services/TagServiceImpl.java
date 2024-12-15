package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.dtos.response.GetAllTagResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateTagRequest;
import com.tugrulhan.freethoughts.mappers.TagMapper;
import com.tugrulhan.freethoughts.models.Tag;
import com.tugrulhan.freethoughts.repositories.TagRepository;
import com.tugrulhan.freethoughts.services.impl.TagService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final Tracer tracer;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, Tracer tracer) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.tracer = tracer;
    }

    @Override
    public List<GetAllTagResponse> getAllTag() {
        Span span=tracer.spanBuilder("getAllTag").startSpan();
        List<Tag> tags = tagRepository.findAll();
        span.addEvent("Tags fetched");
        List<GetAllTagResponse> getAllTagResponses = Collections.singletonList(tagMapper.toGetAllTagResponse(tags));
        span.end();
        return getAllTagResponses;

    }

    @Override
    public void createTag(List<CreateTagRequest> request) {
        Span span=tracer.spanBuilder("createTag").startSpan();
        var tag = tagMapper.toTag(request);
        tagRepository.save(tag);
        span.addEvent("Tag created");
        span.end();
    }
}
