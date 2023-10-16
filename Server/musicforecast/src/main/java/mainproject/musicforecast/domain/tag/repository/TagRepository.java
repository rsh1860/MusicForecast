package mainproject.musicforecast.domain.tag.repository;

import mainproject.musicforecast.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);
}
