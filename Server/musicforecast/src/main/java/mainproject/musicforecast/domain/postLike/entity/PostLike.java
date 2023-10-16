package mainproject.musicforecast.domain.postLike.entity;

import lombok.*;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.post.entity.Post;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    public void setType(LikeType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private PostLike.LikeType type;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

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