package mainproject.musicforecast.domain.keyword.repository;

import mainproject.musicforecast.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findBySongKeyword(String songKeyword);

}
