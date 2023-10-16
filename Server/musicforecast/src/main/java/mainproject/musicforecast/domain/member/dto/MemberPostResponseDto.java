package mainproject.musicforecast.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class MemberPostResponseDto {
    private long memberId;
    private String nickname;
    private String image;
    private List<MemberPostDetailResponseDto> post;
}
