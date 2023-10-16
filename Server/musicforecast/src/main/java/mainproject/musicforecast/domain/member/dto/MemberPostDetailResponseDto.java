package mainproject.musicforecast.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MemberPostDetailResponseDto {
    private Long postId;
    private String title;
    private String text;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
}
