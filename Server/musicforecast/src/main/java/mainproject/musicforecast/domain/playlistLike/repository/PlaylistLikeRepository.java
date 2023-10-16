package mainproject.musicforecast.domain.playlistLike.repository;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, Long> {

    PlaylistLike findByMemberAndPlaylist(Member member, Playlist playlist);
}
