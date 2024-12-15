package com.tugrulhan.freethoughts.services.impl;

import com.tugrulhan.freethoughts.dtos.reuqest.AddLikeRequest;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    void addLike(AddLikeRequest request);
}
