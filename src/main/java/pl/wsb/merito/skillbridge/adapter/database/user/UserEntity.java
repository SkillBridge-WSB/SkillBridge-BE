package pl.wsb.merito.skillbridge.adapter.database.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.model.User;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;
import pl.wsb.merito.skillbridge.rest.response.UserListItemResponse;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private List<SubjectEntity> subjects;


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
                .subjects(subjects.stream().map(SubjectEntity::toDomain).toList())
                .build();
    }

    public TutorListItemResponse toTutorItemListResponse() {
        return TutorListItemResponse.builder()
                .id(this.id.toString())
                .email(this.email)
                .name(this.name)
                .bio(this.bio)
                .imageUrl(this.image_url)
                .subjects(this.subjects.stream().map(SubjectEntity::toSubjectListItem).toList())
                .build();
    }

    public UserListItemResponse toUserListItemResponse() {
        return UserListItemResponse.builder()
                .id(this.id.toString())
                .email(this.email)
                .name(this.name)
                .bio(this.bio)
                .imageUrl(this.image_url)
                .build();
    }
}
