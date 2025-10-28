package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;
import lombok.Data;
import pl.wsb.merito.skillbridge.domain.model.Role;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID id;
    private String email;
    private String name;
    private String bio;
    private String image_url;
    private Role role;


}
