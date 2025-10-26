package pl.wsb.merito.skillbridge.adapter.database.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.model.User;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserEntity {
    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private String bio;
    private String image_url;
    private String role;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long created_at;

    public User toDomain() {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .bio(bio)
                .image_url(image_url)
                .role(Role.getRole(role))
                .created_at(created_at)
                .build();
    }
}
