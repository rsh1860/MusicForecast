package mainproject.musicforecast.domain.recommend.admin;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AdminSuggest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;

    @Column
    private String thumbnailLink;

    @Column
    private String youtubeLink;

    @Column
    private String artist;

    @Column
    private String title;
}
