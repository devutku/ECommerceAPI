package dev.patika.ecommerce.dto.request.user;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSaveRequest {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
