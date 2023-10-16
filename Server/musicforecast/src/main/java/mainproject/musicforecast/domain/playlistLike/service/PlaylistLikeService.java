package mainproject.musicforecast.domain.playlistLike.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.repository.PlaylistRepository;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import mainproject.musicforecast.domain.playlistLike.repository.PlaylistLikeRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaylistLikeService {

    private final PlaylistLikeRepository playlistLikeRepository;
    private final PlaylistRepository playlistRepository;
    private final MemberRepository memberRepository;

    public PlaylistLikeService(PlaylistLikeRepository playlistLikeRepository, PlaylistRepository playlistRepository, MemberRepository memberRepository) {
        this.playlistLikeRepository = playlistLikeRepository;
        this.playlistRepository = playlistRepository;
        this.memberRepository = memberRepository;
    }

    public void likePlaylist(long playlistId, PlaylistLike.LikeType likeType, Member user) {
        Member member = memberRepository.findById(user.getMemberId())
                .orElseThrow(() -> new NullPointerException());
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NullPointerException());

        PlaylistLike existPlaylistLike = playlistLikeRepository.findByMemberAndPlaylist(member, playlist);

        int totalLikeCount = playlist.getLikeCount();

        if (existPlaylistLike != null) {
            totalLikeCount += (likeType == PlaylistLike.LikeType.Like)? -1 : 1;
            playlistLikeRepository.delete(existPlaylistLike);
        } else {
            totalLikeCount += (likeType == PlaylistLike.LikeType.Like)? 1 : -1;
            PlaylistLike playlistLike = PlaylistLike.builder()
                    .type(likeType)
                    .member(member)
                    .playlist(playlist)
                    .build();

            playlistLikeRepository.save(playlistLike);
        }

        playlist.setLikeCount(totalLikeCount);
        playlistRepository.save(playlist);
    }
}
