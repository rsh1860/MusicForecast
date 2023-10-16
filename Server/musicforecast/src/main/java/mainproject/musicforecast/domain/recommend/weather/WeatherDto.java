package mainproject.musicforecast.domain.recommend.weather;

import lombok.Builder;
import lombok.Getter;

public class WeatherDto {

    @Builder
    @Getter
    public static class WeatherResponseDto {

        double temperature;
        String weatherDescription;
    }

    @Builder
    @Getter
    public static class WeatherSongResponseDto {

        String title;
        String album;
        String artist;
        String imageUrl;
    }
}
