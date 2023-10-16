package mainproject.musicforecast.domain.spotify;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotifySearchResponseDto {

    private String artistName;
    private String title;
    private String albumName;
    private String imageUrl;
}
