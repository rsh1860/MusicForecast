package mainproject.musicforecast.domain.song.entity;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistSong.entity.PlaylistSong;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long songId;

    @Column
    private String title;

    @Column
    private String artistName;

    @Column
    private String albumName;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<PlaylistSong> playlistSongs;
}
