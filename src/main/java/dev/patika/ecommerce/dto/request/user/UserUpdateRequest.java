package dev.patika.ecommerce.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserUpdateRequest {
    @Positive
    private int id;
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
