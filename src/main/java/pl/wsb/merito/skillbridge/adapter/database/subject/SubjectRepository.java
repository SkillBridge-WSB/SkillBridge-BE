package pl.wsb.merito.skillbridge.adapter.database.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {

    boolean existsByTutorIdAndName(UUID tutorId, String subjectName);
    List<SubjectEntity> findByTutorId(UUID tutorId);
    Optional<SubjectEntity> findByIdAndTutorId(UUID subjectId, UUID tutorId);
}
