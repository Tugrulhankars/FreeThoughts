package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.response.GetAllTagResponse;
import com.tugrulhan.freethoughts.dtos.reuqest.CreateTagRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

     List<GetAllTagResponse> getAllTag();
     void createTag(List<CreateTagRequest> request);

}
