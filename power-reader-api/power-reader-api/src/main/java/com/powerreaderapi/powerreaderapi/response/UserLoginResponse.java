package com.powerreaderapi.powerreaderapi.response;

import com.powerreaderapi.powerreaderapi.model.User;
import com.powerreaderapi.powerreaderapi.model.enums.Role;

public record UserLoginResponse(Long id, String email, Role role, String tokenJWT) {

    public static UserLoginResponse fromUser(User user, String tokenJWT) {
        return new UserLoginResponse(user.getId(), user.getEmail(), user.getRole(), tokenJWT);
    }

}
