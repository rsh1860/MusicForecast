package mainproject.musicforecast.domain.spotify;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public class SpotifyMapper {

    public SpotifySearchResponseDto toSpotifySearchDto(String artistName, String title, String albumName, String imageUrl) {
        return SpotifySearchResponseDto.builder()
                .artistName(artistName)
                .title(title)
                .albumName(albumName)
                .imageUrl(imageUrl)
                .build();
    }
}
