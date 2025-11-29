package pl.wsb.merito.skillbridge.rest.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    private String email;
    @Size(min = 8, max = 24)
    @NotBlank
    private String password;
    @Size(min = 3, max = 50)
    @NotBlank
    private String name;
    @Nullable
    @Size(max = 250)
    private String bio;
    @Nullable
    private String image_url;
    @Nullable
    private String role;
}