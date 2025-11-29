package pl.wsb.merito.skillbridge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.ArrayList;
import java.util.List;
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
    private List<Subject> subjects;

    public UserEntity toEntity(){
        if (subjects == null) {
            subjects = new ArrayList<>();
        }

        return new UserEntity(id, email, password, name, bio, image_url, role.toString(), created_at, subjects.stream().map(s -> s.toEntity(this)).toList(), null);
    }

    public Response.User toApiResponse() {
        return Response.User.builder()
                .id(id)
                .email(email)
                .name(name)
                .bio(bio)
                .imageUrl(image_url)
                .role(role.toString())
                .build();
    }
}
