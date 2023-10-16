package mainproject.musicforecast.domain.playlistTag.service;

import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import mainproject.musicforecast.domain.playlistTag.repository.PlaylistTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistTagService {

    private final PlaylistTagRepository playlistTagRepository;

    public PlaylistTagService(PlaylistTagRepository playlistTagRepository) {
        this.playlistTagRepository = playlistTagRepository;
    }

    public void clearPlaylistTag(Playlist playlist) {
        List<PlaylistTag> playlistTags = playlist.getPlaylistTags();
        for (PlaylistTag playlistTag : playlistTags) {
            long playlistTagId = playlistTag.getPlaylistTagId();
            playlistTagRepository.deleteById(playlistTagId);
        }
    }
}