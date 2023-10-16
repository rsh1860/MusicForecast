package mainproject.musicforecast.domain.post.dto;

import lombok.Getter;
import mainproject.musicforecast.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class PostPostDto {
    @Positive
    private Long memberId;

    @NotBlank(message = "글을 작성해주세요.")
    private String title;

    @NotBlank(message = "글을 작성해주세요.")
    private String text;

    @Positive
    private Long playlistId; // 플레이리스트 ID 필드 추가


    public Long getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }


    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }
    public Long getPlaylistId() {
        return playlistId;
    }

    public Member getMember(){
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}