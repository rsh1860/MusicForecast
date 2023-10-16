package mainproject.musicforecast.domain.song.controller;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import mainproject.musicforecast.domain.keyword.service.KeywordService;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.service.PlaylistService;
import mainproject.musicforecast.domain.playlistSong.entity.PlaylistSong;
import mainproject.musicforecast.domain.playlistSong.service.PlaylistSongService;
import mainproject.musicforecast.domain.song.dto.SongDto;
import mainproject.musicforecast.domain.song.entity.Song;
import mainproject.musicforecast.domain.song.mapper.SongMapper;
import mainproject.musicforecast.domain.song.service.SongService;
import mainproject.musicforecast.domain.spotify.SpotifyMapper;
import mainproject.musicforecast.domain.spotify.SpotifySearchResponseDto;
import mainproject.musicforecast.domain.spotify.SpotifyService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;
    private final SpotifyService spotifyService;
    private final KeywordService keywordService;
    private final PlaylistSongService playlistSongService;
    private final PlaylistService playlistService;
    private final SpotifyMapper mapper;
    private final SongMapper songMapper;


    public SongController(SongService songService, SpotifyService spotifyService, KeywordService keywordService, PlaylistSongService playlistSongService,PlaylistService playlistService, SpotifyMapper mapper, SongMapper songMapper) {
        this.songService = songService;
        this.spotifyService = spotifyService;
        this.keywordService = keywordService;
        this.playlistSongService = playlistSongService;
        this.playlistService = playlistService;
        this.mapper = mapper;
        this.songMapper = songMapper;
    }

    @GetMapping("/search")
    public List<SongDto.SpotifyAddResponseDto> search(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "10") int song) {
        List<SongDto.SpotifyAddResponseDto> addResponseDtoList = new ArrayList<>();

        try {
            SearchTracksRequest searchTracksRequest = spotifyService.setSpotifyApi().searchTracks(keyword)
                    .limit(song)
                    .build();

            Paging<Track> searchResult = searchTracksRequest.execute();
            Track[] tracks = searchResult.getItems();

            String title = "";
            String albumName = "";
            String artistName = "";

            for (Track track : tracks) {
                title = track.getName();

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();
                artistName = artists[0].getName();


                Image[] images = album.getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

                albumName = album.getName();

                SpotifySearchResponseDto spotifySearchResultDto = mapper.toSpotifySearchDto(artistName, title, albumName, imageUrl);

                if (!songService.findDuplicatedSong(title, artistName, albumName)) { // 이미 존재하는 노래가 아닐 때
//                    songService.findAndDeleteDuplicatedSong(title, albumName, artistName);
                    songService.createSong(songMapper.spotifySearchResponseDtoToSong(spotifySearchResultDto));
                }
                long songId = songService.findSongId(title, artistName, albumName).getSongId();
                System.out.println("song Id : " + songId);
                SongDto.SpotifyAddResponseDto spotifyAddResponseDto = songMapper.toSpotifyAddResponseDto(songId, artistName, title, albumName, imageUrl);
                addResponseDtoList.add(spotifyAddResponseDto);
            }
            if (!keywordService.findKeyword(keyword)) { // 중복된 키워드가 아닐때
                SongDto.KeywordResponse keywordResponse = SongDto.KeywordResponse.builder()
                        .keyword(keyword).build();
                keywordService.createKeyword(songMapper.keywordDtoToKeyword(keywordResponse));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return addResponseDtoList;
    }

    @PostMapping("/{song-id}/{playlist-id}/add")
    public ResponseEntity addSongToPlaylist(@PathVariable("song-id") long songId,
                                            @PathVariable("playlist-id") long playlistId,
                                            @AuthenticationPrincipal Member member) {
        Song findSong = songService.findSongById(songId).orElseThrow(() -> new NullPointerException());
        Playlist findPlaylist = playlistService.findPlaylistById(playlistId).orElseThrow(() -> new NullPointerException());

        PlaylistSong playlistSong = new PlaylistSong();

        playlistSong.setSong(findSong);
        playlistSong.setPlaylist(findPlaylist);
        playlistSong.setSongTitle(findSong.getTitle());
        playlistSong.setArtistName(findSong.getArtistName());
        playlistSong.setAlbumName(findSong.getAlbumName());
        playlistSong.setImageUrl(findSong.getImageUrl());

        if (songService.checkExistSong(findSong.getSongId(), findPlaylist.getPlaylistId())) return null;
        System.out.println("check SONG : " + playlistSong);

        playlistSongService.addToPlaylistSong(playlistSong, member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{playlist-id}/{song-id}/delete")
    public ResponseEntity deleteSongFromPlaylist(@PathVariable("playlist-id") long playlistId,
                                                 @PathVariable("song-id") long songId,
                                                 @AuthenticationPrincipal Member member) {
        playlistSongService.deleteFromPlaylistSong(playlistId, songId, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
