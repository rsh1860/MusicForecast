package mainproject.musicforecast.domain.recommend.youtuber;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter @Setter
public class YoutuberResponseDto {

    private long youtuberId;
    private String youtuberName;
    private String youtuberLink;
    private String youtuberImage;
}
