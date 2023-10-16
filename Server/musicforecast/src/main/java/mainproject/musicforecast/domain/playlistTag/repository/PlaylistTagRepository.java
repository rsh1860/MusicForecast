package mainproject.musicforecast.domain.playlistTag.repository;

import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistTagRepository extends JpaRepository<PlaylistTag, Long> {

    @Query("SELECT p FROM PlaylistTag p WHERE p.tag.tagName IN :tags")
    Page<PlaylistTag> findByTags(Pageable pageable, List<String> tags);

    @Query("SELECT p FROM PlaylistTag p WHERE p.tag.tagName = :tag")
    Page<PlaylistTag> findByTag(Pageable pageable, String tag);
}
