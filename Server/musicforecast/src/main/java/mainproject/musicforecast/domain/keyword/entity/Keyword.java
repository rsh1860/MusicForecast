package mainproject.musicforecast.domain.keyword.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long keywordId;

    @Column
    private String songKeyword;
}
