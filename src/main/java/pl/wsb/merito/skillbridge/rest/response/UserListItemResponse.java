package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserListItemResponse {

    private String id;
    private String email;
    private String name;
    private String bio;
    private String imageUrl;
}
