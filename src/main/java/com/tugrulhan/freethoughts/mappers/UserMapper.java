package com.tugrulhan.freethoughts.mappers;

import com.tugrulhan.freethoughts.dtos.response.GetAllUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public GetAllUserResponse toGetAllUserResponse(com.tugrulhan.freethoughts.models.User user) {
        GetAllUserResponse response = new GetAllUserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        return  response;
    }
}
