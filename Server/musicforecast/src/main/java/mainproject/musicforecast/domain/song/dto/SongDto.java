package mainproject.musicforecast.domain.song.dto;

import lombok.Builder;
import lombok.Getter;

public class SongDto {


    @Builder
    @Getter
    public static class KeywordResponse {
        String keyword;
    }


    @Builder
    @Getter
    public static class SpotifyAddResponseDto {

        private long songId;
        private String artistName;
        private String title;
        private String albumName;
        private String imageUrl;
    }
}
