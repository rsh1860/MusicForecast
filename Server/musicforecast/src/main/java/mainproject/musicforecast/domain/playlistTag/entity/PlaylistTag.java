package mainproject.musicforecast.domain.playlistTag.entity;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.tag.entity.Tag;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PlaylistTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playlistTagId;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @Column
    private String tagName;
}
