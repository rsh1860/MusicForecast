package mainproject.musicforecast.domain.playlist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import mainproject.musicforecast.domain.tag.entity.Tag;

import java.util.List;

public class PlaylistDto {

    @Getter
    @Setter
    public static class Post {
        private String title;
        private boolean isPublic;
    }

    @Getter
    @Setter
    public static class Patch {
        private long playlistId;
        private String title;
        private boolean isPublic;
//        private List<PlaylistTag> playlistTags;
    }

    @Builder
    @Getter @Setter
    public static class PatchTag {
        private long playlistId;
        private String title;
        private boolean isPublic;
        private List<String> tag;
    }

    /*
    * 좋아요 누를 때 보내는 값
    */
    @Builder
    @Getter
    public static class Like {
        private boolean like;
    }
    
    /*
    * 플레이리스트 목록에서 조회시
    */
    @Builder
    @Getter @Setter
    public static class Response {
        private long playlistId;
        private String title;
        private long memberId;
    }

    /*
     * 플레이리스트 개별 조회시
     */
    @Builder
    @Getter @Setter
    public static class DetailResponse {
        private long playlistId;
        private String title;
        private boolean isPublic;
        private int view;
        private int like;
        private long memberId;
        private List<PlaylistTagResponse> playlistTags;
        private List<PlaylistSongResponse> playlistSongs;
    }

    @Builder
    @Getter @Setter
    public static class PlaylistTagResponse {
        private long playlistTagId;
        private long playlistId;
        private long tagId;
        private String tagName;
    }

    @Builder
    @Getter @Setter
    public static class PlaylistSongResponse {
        private long playlistSongId;
        private long songId;
        private String title;
        private String albumName;
        private String artistName;
        private String imageUrl;
        private String youtubeUrl;
    }

}
