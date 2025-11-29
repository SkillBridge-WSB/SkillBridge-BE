package pl.wsb.merito.skillbridge.domain.model;

import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private Set<User> tutors; // as student
    private Set<User> students; // as tutor

    public UserEntity toEntity() {
        if (subjects == null) {
            subjects = new ArrayList<>();
        }
        if (tutors == null) {
            tutors = Set.of();
        }
        if (students == null) {
            students = Set.of();
        }

        return new UserEntity(id, email, password, name, bio, image_url, role.toString(), created_at,
                subjects
                        .stream()
                        .map(s -> s.toEntity(this))
                        .toList(),
                tutors
                        .stream()
                        .map(User::toEntity)
                        .collect(Collectors.toSet()),
                students
                        .stream()
                        .map(User::toEntity)
                        .collect(Collectors.toSet()));
    }

    public Set<User> getMatches() {
        if (role.equals(Role.STUDENT)) {
            return tutors;
        } else {
            return students;
        }
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

    // Lombok @Getter override to prevent using this methods - use getMatches() instead
    private Set<User> getTutors() {
        throw new ValidationException();
    }

    private Set<User> getStudents() {
        throw new ValidationException();
    }

}
