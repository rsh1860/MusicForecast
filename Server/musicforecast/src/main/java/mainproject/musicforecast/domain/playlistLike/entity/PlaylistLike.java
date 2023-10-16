package mainproject.musicforecast.domain.playlistLike.entity;

import lombok.*;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.playlist.entity.Playlist;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistLikeId;

    public void setType(LikeType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private LikeType type;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist playlist;

    @RequiredArgsConstructor
    public enum LikeType {
        Like("좋아요");

        private final String stepDescription;

        public String getName() {
            return name();
        }

        public String getDescription() {
            return this.stepDescription;
        }
    }
}
