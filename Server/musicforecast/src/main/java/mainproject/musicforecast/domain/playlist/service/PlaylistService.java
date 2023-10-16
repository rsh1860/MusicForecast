package mainproject.musicforecast.domain.playlist.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.dto.PlaylistDto;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.repository.PlaylistRepository;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import mainproject.musicforecast.domain.playlistTag.service.PlaylistTagService;
import mainproject.musicforecast.domain.tag.entity.Tag;
import mainproject.musicforecast.domain.tag.repository.TagRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TagRepository tagRepository;
    private final PlaylistTagService playlistTagService;

    public PlaylistService(PlaylistRepository playlistRepository, TagRepository tagRepository, PlaylistTagService playlistTagService) {
        this.playlistRepository = playlistRepository;
        this.tagRepository = tagRepository;
        this.playlistTagService = playlistTagService;
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist findPlaylist(long playlistId) {
        Playlist findPlaylist = findByPlaylistId(playlistId);
        findPlaylist.setView(findPlaylist.getView() + 1);
        return findPlaylist;
    }

    private Playlist findByPlaylistId(long playlistId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        Playlist findPlaylist = optionalPlaylist.orElseThrow(() -> new NullPointerException());
        return findPlaylist;
    }

    public Page<Playlist> findMyPlaylists(int page, int size, long memberId) {
        return playlistRepository.findMyAll(
                PageRequest.of(page, size, Sort.by("playlistId").descending()), memberId
        );
    }

    public Page<Playlist> findPlaylists(int page, int size) {
        return playlistRepository.findAllPublic(
                PageRequest.of(page, size, Sort.by("playlistId").descending()), true
        );
    }

    public Playlist updatePlaylist(Playlist playlist) {
        Playlist findPlaylist = findVerifiedPlaylist(playlist.getPlaylistId());

        Optional.ofNullable(playlist.getTitle()).ifPresent(title -> findPlaylist.setTitle(title));
        Optional.ofNullable(playlist.isPublic()).ifPresent(isPublic -> findPlaylist.setPublic(isPublic));

        return playlistRepository.save(findPlaylist);
    }

    private Playlist findVerifiedPlaylist(long playlistId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        Playlist findPlaylist = optionalPlaylist.orElseThrow(() -> new NullPointerException());
        return findPlaylist;
    }

    public Playlist updatePlaylistWithTags(PlaylistDto.PatchTag playlistPatchDto, Member member) {
        Playlist playlist = playlistRepository.findById(playlistPatchDto.getPlaylistId()).orElse(null);

        if (!playlist.getMember().getMemberId().equals(member.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        Optional.ofNullable(playlistPatchDto.getPlaylistId()).ifPresent(playlistId -> playlist.setPlaylistId(playlistId));
        Optional.ofNullable(playlistPatchDto.getTitle()).ifPresent(title -> playlist.setTitle(title));
        Optional.ofNullable(playlistPatchDto.isPublic()).ifPresent(isPublic -> playlist.setPublic(isPublic));

        if (playlist != null) {
            playlistTagService.clearPlaylistTag(playlist);
            playlist.getPlaylistTags().clear();

//            List<PlaylistTag> newTags = new ArrayList<>();
//            for (String tags : playlistPatchDto.getTag()) {
//                Tag tag = tagRepository.findByTagName(tags).orElse(null);
//                if (tag != null) {
//                    PlaylistTag playlistTag = new PlaylistTag();
//                    playlistTag.setPlaylist(playlist);
//                    playlistTag.setTag(tag);
//                    playlistTag.setTagName(tag.getTagName());
//                    newTags.add(playlistTag);
//                }
//            }
//            playlist.updateTags(newTags);

            PlaylistTag[] newTags = new PlaylistTag[3];
            int count = 0;
            List<String> tags = playlistPatchDto.getTag();
            int minNum = 3 < tags.size() ? 3 : tags.size();
            for (int i = 0; i < minNum; i++) {
                Tag tag = tagRepository.findByTagName(tags.get(i)).orElse(null);
                if (tag != null) {
                    PlaylistTag playlistTag = new PlaylistTag();
                    playlistTag.setPlaylist(playlist);
                    playlistTag.setTag(tag);
                    playlistTag.setTagName(tag.getTagName());
                    newTags[i] = playlistTag;
                }
            }
            List<PlaylistTag> newTagList = new ArrayList<>();
            for (PlaylistTag tag : newTags) {
                if (tag != null) newTagList.add(tag);
            }
            playlist.updateTags(newTagList);
        }
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(long playlistId, Member member) {
        Playlist playlist = findVerifiedPlaylist(playlistId);

        if (!playlist.getMember().getMemberId().equals(member.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        playlistRepository.delete(playlist);
    }

    public Optional<Playlist> findPlaylistById(long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    public Page<Playlist> searchPlaylist(int page, int size, String keyword) {
        return playlistRepository.findByKeyword(
                PageRequest.of(page, size, Sort.by("playlistId").descending()), keyword
        );
    }
}
