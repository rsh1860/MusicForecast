package mainproject.musicforecast.domain.recommend.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mainproject.musicforecast.domain.playlist.Utils;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.mapper.PlaylistMapper;
import mainproject.musicforecast.domain.playlistTag.repository.PlaylistTagRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final PlaylistMapper mapper;

    public WeatherController(WeatherService weatherService, PlaylistMapper mapper) {
        this.weatherService = weatherService;
        this.mapper = mapper;
    }

    @GetMapping("/result")
    public ResponseEntity recommendPlaylist(@RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @RequestParam("q") String weather) {
        Page<Playlist> playlistPage = weatherService.findBySearchTags(page - 1, size, weather);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(
                new Utils.MultiResponseDto<>(mapper.playlistToPlaylistResponseDtos(playlists), playlistPage), HttpStatus.OK
        );
    }


    @GetMapping("/data")
    public ResponseEntity getWeatherData() {
        String jsonString = weatherService.getWeatherString("1835848"); //seoul

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            double temperature = jsonNode.get("main").get("temp").asDouble();
            double cTemperature = temperature - 273.15;
            String weatherDescription = jsonNode.get("weather").get(0).get("main").asText();

            WeatherDto.WeatherResponseDto weatherResponseDto = new WeatherDto.WeatherResponseDto(/*cityName, */Math.round(cTemperature), weatherDescription);

            return new ResponseEntity<>(weatherResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
