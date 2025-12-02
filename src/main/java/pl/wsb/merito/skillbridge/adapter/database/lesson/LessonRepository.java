package pl.wsb.merito.skillbridge.adapter.database.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    Optional<LessonEntity> findByIdAndTutorId(UUID lessonId, UUID userId);
    Optional<LessonEntity> findByIdAndStudentId(UUID lessonId, UUID userId);
    
    @Query("SELECT l FROM LessonEntity l " +
           "LEFT JOIN FETCH l.student " +
           "LEFT JOIN FETCH l.tutor " +
           "LEFT JOIN FETCH l.subject " +
           "LEFT JOIN FETCH l.slot " +
           "WHERE l.student.id = :studentId")
    List<LessonEntity> findByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT l FROM LessonEntity l " +
           "LEFT JOIN FETCH l.student " +
           "LEFT JOIN FETCH l.tutor " +
           "LEFT JOIN FETCH l.subject " +
           "LEFT JOIN FETCH l.slot " +
           "WHERE l.tutor.id = :tutorId")
    List<LessonEntity> findByTutorId(@Param("tutorId") UUID tutorId);
}
