package mainproject.musicforecast.domain.spotify;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SpotifyController {

    private final SpotifyService spotifyService;
    private final SpotifyMapper mapper;

    public SpotifyController(SpotifyService spotifyService, SpotifyMapper mapper) {
        this.spotifyService = spotifyService;
        this.mapper = mapper;
    }

    @GetMapping("/search/spotify/{keyword}")
    public List<SpotifySearchResponseDto> search(@PathVariable("keyword") String keyword) {
        List<SpotifySearchResponseDto> searchResponseDtoList = new ArrayList<>();

        try {
            SearchTracksRequest searchTracksRequest = spotifyService.setSpotifyApi().searchTracks(keyword)
                    .limit(10)
                    .build();

            Paging<Track> searchResult = searchTracksRequest.execute();
            Track[] tracks = searchResult.getItems();

            for (Track track : tracks) {
                String title = track.getName();

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();
                String artistName = artists[0].getName();


                Image[] images = album.getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

                String albumName = album.getName();

                searchResponseDtoList.add(mapper.toSpotifySearchDto(artistName, title, albumName, imageUrl));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return searchResponseDtoList;
    }
}
