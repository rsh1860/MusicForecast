package mainproject.musicforecast.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.comment.dto.CommentResponseDto;
import mainproject.musicforecast.domain.member.dto.MemberResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String text;
    private List<CommentResponseDto> comments;

    private Long memberId;
    private String nickName;
    //private int voteCount;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private LocalDateTime createdAt;

    private Long playlistId;
    private String playlistTitle;

    private boolean hasLiked;

    public boolean isHasLiked(){
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked){
        this.hasLiked = hasLiked;
    }

    public void setMember(MemberResponseDto memberToMemberResponseDto) {
        this.nickName = memberToMemberResponseDto.getNickname();
        this.memberId = memberToMemberResponseDto.getMemberId();
    }
}