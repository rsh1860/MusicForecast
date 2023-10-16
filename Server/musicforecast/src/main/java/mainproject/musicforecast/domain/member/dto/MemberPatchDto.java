package mainproject.musicforecast.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberPatchDto {
    //private String image; 프로필 이미지 수정 시 필요 어떻게 하는지 확인 필요!
    private long memberId;
    private String intro;
    private String nickname;
    private String image;
}
