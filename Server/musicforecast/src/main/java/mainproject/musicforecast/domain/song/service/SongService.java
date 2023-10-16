package mainproject.musicforecast.domain.song.service;

import mainproject.musicforecast.domain.song.entity.Song;
import mainproject.musicforecast.domain.song.repository.SongRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> findSongById(long songId) {
        return songRepository.findById(songId);
    }

    public Song findSongId(String title, String artistName, String albumName) {
        return songRepository.findSongId(title, artistName, albumName);
    }

    public void findAndDeleteDuplicatedSong(String title, String albumName, String artistName) {
//        List<Song> duplicateSongs = songRepository.findDuplicateSongs(title, albumName, artistName);
//
//        if (!duplicateSongs.isEmpty()) songRepository.deleteDuplicateSongs(duplicateSongs);
        songRepository.deleteDuplicateSongs(title, artistName, albumName);
    }

    public boolean findDuplicatedSong(String title, String artistName, String albumName) {
        Optional<Song> duplicatedSong = songRepository.findDuplicatedSong(title, artistName, albumName);
        if (duplicatedSong.isPresent()) return true;
        return false;
    }

    public boolean checkExistSong(long songId, long playlistId) {
        if (songRepository.findExistSong(songId, playlistId) != null) return true;
        return false;
    }
}
