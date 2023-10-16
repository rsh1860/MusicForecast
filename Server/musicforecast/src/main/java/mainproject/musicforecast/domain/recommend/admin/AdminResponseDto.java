package mainproject.musicforecast.domain.recommend.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Builder
@Getter @Setter
public class AdminResponseDto {

    private long adminId;
    private String thumbnailLink;
    private String youtubeLink;
    private String artist;
    private String title;
}
