package pl.wsb.merito.skillbridge.adapter.database.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    Optional<LessonEntity> findByIdAndTutorId(UUID lessonId, UUID userId);
    Optional<LessonEntity> findByIdAndStudentId(UUID lessonId, UUID userId);
}
