package mainproject.musicforecast.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlistLike.entity.PlaylistLike;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.provider.Provider;
import mainproject.musicforecast.domain.question.Question;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    @Email
    private String email;

    @Column
    private String nickname;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    private Long birthdate;

    @Column
    private Long questionNumber;

    @Column(name = "auth_answer")
    private String authAnswer;

    @Column
    private String intro;

    @Column
    private String image;

    @Column
    private BigInteger sub; // 구글 회원 고유 ID

    @Column
    private BigInteger kakaoId; // KAKAO 회원 고유 ID

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "member")
    private List<PlaylistLike> playlistLikes;

    @OneToMany(mappedBy = "member")
    private List<Post> post;

    @OneToOne
    @JoinColumn(name = "PROVIDER_ID")
    private Provider provider;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
