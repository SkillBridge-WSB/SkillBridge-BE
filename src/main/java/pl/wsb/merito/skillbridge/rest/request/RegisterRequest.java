package pl.wsb.merito.skillbridge.rest.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String bio;
    private String image_url;
    private String role;
}