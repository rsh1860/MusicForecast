package mainproject.musicforecast.domain.recommend.youtuber;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Youtuber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long youtuberId;

    @Column
    private String youtuberName;

    @Column
    private String youtuberLink;

    @Column
    private String youtuberImage;
}
