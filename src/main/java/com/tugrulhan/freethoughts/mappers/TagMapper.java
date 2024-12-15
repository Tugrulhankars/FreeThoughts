package com.tugrulhan.freethoughts.mappers;

import com.tugrulhan.freethoughts.dtos.response.GetAllTagResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateTagRequest;
import com.tugrulhan.freethoughts.models.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {


    public GetAllTagResponse toGetAllTagResponse(List<Tag> tag) {
        GetAllTagResponse getAllTagResponse = new GetAllTagResponse(
                tag.stream().map(Tag::getTagName).toList()
        );

        return getAllTagResponse;

    }


    public Tag toTag(List<CreateTagRequest> createTagRequest) {
        Tag tag = new Tag();
        tag.setTagName(createTagRequest.stream().map(CreateTagRequest::tagName).toList().get(0));
        return tag;
    }
}
