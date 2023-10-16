package mainproject.musicforecast.domain.comment.repository;

import mainproject.musicforecast.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
