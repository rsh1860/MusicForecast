package mainproject.musicforecast.domain.playlist.entity;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import mainproject.musicforecast.domain.playlistSong.entity.PlaylistSong;
import mainproject.musicforecast.domain.playlistTag.entity.PlaylistTag;
import mainproject.musicforecast.domain.post.entity.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playlistId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isPublic;

    @Column(nullable = false)
    private int view = 0;

    @Column(nullable = false)
    private int likeCount = 0;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistLike> playlistLikes;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistTag> playlistTags;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistSong> playlistSongs;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    public void updateTags(List<PlaylistTag> newTags) {
        this.playlistTags.clear();
        this.playlistTags.addAll(newTags);
    }
}
