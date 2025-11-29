package pl.wsb.merito.skillbridge.adapter.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u from UserEntity u LEFT JOIN FETCH u.subjects s WHERE u.role = :role AND s.id IN :subjectIds")
    List<UserEntity> findAllByRoleAndSubjectIds(String role, List<UUID> subjectIds);
}
