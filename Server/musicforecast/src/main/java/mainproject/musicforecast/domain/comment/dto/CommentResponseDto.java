package mainproject.musicforecast.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long memberId;
    private String nickname;
    private Long postId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
