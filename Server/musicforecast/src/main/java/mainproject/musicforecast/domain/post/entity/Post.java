package mainproject.musicforecast.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mainproject.musicforecast.domain.audit.Auditable;
import mainproject.musicforecast.domain.comment.entity.Comment;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.postLike.entity.PostLike;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    //private int voteCount;

    @Column(nullable = false)
    private int likeCount = 0;


    private int viewCount;

    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikes;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;


    // 연관 관계 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void setPlaylists(Playlist playlist) { this.playlist = playlist; }
}