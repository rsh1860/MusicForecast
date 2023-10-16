package mainproject.musicforecast.domain.playlistSong.entity;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.song.entity.Song;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playlistSongId;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "SONG_ID")
    private Song song;

    @Column
    private String songTitle;

    @Column
    private String artistName;

    @Column
    private String albumName;

    @Column
    private String imageUrl;

    @Column
    private String youtubeUrl;
}
