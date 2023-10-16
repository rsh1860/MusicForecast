package mainproject.musicforecast.domain.comment.entity;

import mainproject.musicforecast.domain.audit.Auditable;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column
    private String text;

    @Column
    private String nickname;

    public Long getMemberId() {
        return member.getMemberId();
    }

    public Long getPostId() {
        return post.getPostId();
    }
}
