package dev.patika.ecommerce.dto.request.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class LoginRequest {
    private String username;

    private String password;

}