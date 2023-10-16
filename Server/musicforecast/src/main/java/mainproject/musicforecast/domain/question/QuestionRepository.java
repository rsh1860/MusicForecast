package mainproject.musicforecast.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByQuestionId(long questionId);
}
