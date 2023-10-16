package mainproject.musicforecast.domain.playlistSong.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlistSong.entity.PlaylistSong;
import mainproject.musicforecast.domain.playlistSong.repository.PlaylistSongRepository;
import mainproject.musicforecast.domain.song.entity.Song;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

@Service
public class PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;

    public PlaylistSongService(PlaylistSongRepository playlistSongRepository) {
        this.playlistSongRepository = playlistSongRepository;
    }

    public void addToPlaylistSong(PlaylistSong playlistSong, Member member) {
        if (!playlistSong.getPlaylist().getMember().getMemberId().equals(member.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }
        playlistSongRepository.save(playlistSong);
    }

//    public void deleteFromPlaylistSong(long playlistId, long songId) {
//        playlistSongRepository.deleteByPlaylistIdAndSongId(playlistId, songId);
//    }

    public void deleteFromPlaylistSong(long playlistId, long songId, Member member) {
        PlaylistSong playlistSong = playlistSongRepository.findByPlaylistIdAndSongId(playlistId, songId);

        if (!playlistSong.getPlaylist().getMember().getMemberId().equals(member.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }
        playlistSongRepository.deleteById(playlistSong.getPlaylistSongId());
    }
}
