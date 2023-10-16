package mainproject.musicforecast.domain.song.repository;

import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistSong.entity.PlaylistSong;
import mainproject.musicforecast.domain.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

//    @Query("SELECT s FROM Song s WHERE s.title = :title AND s.albumName = :albumName AND s.artistName = :artistName GROUP BY s.title, s.albumName, s.artistName HAVING COUNT(s) > 1")
//    List<Song> findDuplicateSongs(@Param("title") String title, @Param("albumName") String albumName, @Param("artistName") String artistName);
//
//    @Modifying
//    @Query("DELETE FROM Song s WHERE s IN :duplicates")
//    void deleteDuplicateSongs(@Param("duplicates") List<Song> duplicates);

    @Modifying
    @Query("DELETE FROM Song s WHERE s.title=:title AND s.artistName=:artistName AND s.albumName=:albumName")
    void deleteDuplicateSongs(@Param("title") String title, @Param("artistName") String artistName, @Param("albumName") String albumName);

    @Query("SELECT s FROM Song s WHERE s.title=:title AND s.artistName=:artistName AND s.albumName=:albumName")
    Optional<Song> findDuplicatedSong(@Param("title") String title, @Param("artistName") String artistName, @Param("albumName") String albumName);

    @Query("SELECT p FROM PlaylistSong p WHERE p.song.songId=:songId AND p.playlist.playlistId=:playlistId")
    PlaylistSong findExistSong(long songId, long playlistId);


    @Query("SELECT s FROM Song s WHERE s.title=:title AND s.artistName=:artistName AND s.albumName=:albumName")
    Song findSongId(@Param("title") String title, @Param("artistName") String artistName, @Param("albumName") String albumName);
}
