package com.powerreaderapi.powerreaderapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.powerreaderapi.powerreaderapi.util.ErrorMessages.WRONG_EMAIL;
import static com.powerreaderapi.powerreaderapi.util.ErrorMessages.WRONG_PASSWORD;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @Email(message = WRONG_EMAIL)
    @NotBlank(message = WRONG_EMAIL)
    private final String email;

    @NotBlank(message = WRONG_PASSWORD)
    private final String password;

}
