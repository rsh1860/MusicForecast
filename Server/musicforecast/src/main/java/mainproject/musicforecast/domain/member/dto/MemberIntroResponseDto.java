package mainproject.musicforecast.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class MemberIntroResponseDto {
    private long memberId;
    private String nickname;
    private String intro;
    private String image;
}
