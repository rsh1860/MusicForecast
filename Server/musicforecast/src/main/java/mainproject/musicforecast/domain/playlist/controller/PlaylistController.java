package mainproject.musicforecast.domain.playlist.controller;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.Utils;
import mainproject.musicforecast.domain.playlist.dto.PlaylistDto;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.mapper.PlaylistMapper;
import mainproject.musicforecast.domain.playlist.service.PlaylistService;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import mainproject.musicforecast.domain.playlistLike.service.PlaylistLikeService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlist")
@Validated
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistLikeService playlistLikeService;
    private final PlaylistMapper mapper;

    public PlaylistController(PlaylistService playlistService, PlaylistLikeService playlistLikeService, PlaylistMapper mapper) {
        this.playlistService = playlistService;
        this.playlistLikeService = playlistLikeService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postPlaylist(@Valid @RequestBody PlaylistDto.Post playlistPostDto,
                                       @AuthenticationPrincipal Member member) {
        Playlist playlist = playlistService.createPlaylist(mapper.playlistPostDtoToPlaylist(playlistPostDto, member));
        URI location = Utils.createUri("/playlist", playlist.getPlaylistId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{playlist-id}")
    public ResponseEntity getPlaylist(@PathVariable("playlist-id") @Positive long playlistId) {
        Playlist playlist = playlistService.findPlaylist(playlistId);
        return new ResponseEntity<>(
                new Utils.SingleResponseDto<>(mapper.playlistToPlaylistResponseDto(playlist)), HttpStatus.OK
        );
    }

    @GetMapping("/my")
    public ResponseEntity getMyPlaylists(@RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @AuthenticationPrincipal Member member) {
        long memberId = member.getMemberId();
        Page<Playlist> playlistPage = playlistService.findMyPlaylists(page - 1, size, memberId);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(
                new Utils.MultiResponseDto<>(mapper.playlistToPlaylistResponseDtos(playlists), playlistPage), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getPlaylists(@RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size) {
        Page<Playlist> playlistPage = playlistService.findPlaylists(page - 1, size);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(
                new Utils.MultiResponseDto<>(mapper.playlistToPlaylistResponseDtos(playlists), playlistPage), HttpStatus.OK
        );
    }

//    @PatchMapping("/{playlist-id}")
//    public ResponseEntity patchPlaylist(@PathVariable("playlist-id") @Positive long playlistId,
//                                        @Valid @RequestBody PlaylistDto.Patch playlistPatchDto) {
//        playlistPatchDto.setPlaylistId(playlistId);
//
//        Playlist playlist = playlistService.updatePlaylist(mapper.playlistPatchDtoToPlaylist(playlistPatchDto));
//
//        return new ResponseEntity<>(
//                new Utils.SingleResponseDto<>(mapper.playlistToPlaylistResponseDto(playlist)), HttpStatus.OK
//        );
//    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{playlist-id}")
    public ResponseEntity patchPlaylistWithTags(@PathVariable("playlist-id") long playlistId,
                                                @RequestBody PlaylistDto.PatchTag playlistPatchDto,
                                                @AuthenticationPrincipal Member member) {
        playlistPatchDto.setPlaylistId(playlistId);
        Playlist playlist = playlistService.updatePlaylistWithTags(playlistPatchDto, member);
        return new ResponseEntity<>(
                new Utils.SingleResponseDto<>(mapper.playlistToPlaylistResponseDto(playlist)), HttpStatus.OK
        );
    }


    @DeleteMapping("/{playlist-id}")
    public ResponseEntity deletePlaylist(@PathVariable("playlist-id") @Positive long playlistId,
                                         @AuthenticationPrincipal Member member) {
        playlistService.deletePlaylist(playlistId, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{playlist-id}/like")
    public ResponseEntity likePlaylist(@PathVariable("playlist-id") long playlistId,
                                       @AuthenticationPrincipal Member member) {
        playlistLikeService.likePlaylist(playlistId, PlaylistLike.LikeType.Like, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity searchPlaylist(@RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam String keyword) {
        Page<Playlist> playlistPage = playlistService.searchPlaylist(page - 1, size, keyword);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(
                new Utils.MultiResponseDto<>(mapper.playlistToPlaylistResponseDtos(playlists), playlistPage), HttpStatus.OK
        );
    }
}
