package pl.wsb.merito.skillbridge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;

import java.util.UUID;

@ToString
@Getter
@Builder
public class User {
    @NonNull
    private UUID id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String name;
    private String bio;
    private String image_url;
    @NonNull
    private Role role;
    private Long created_at; // epoch millis

    public UserEntity toEntity(){
        return new UserEntity(id, email, password, name, bio, image_url, role.toString(), created_at);
    }
}
