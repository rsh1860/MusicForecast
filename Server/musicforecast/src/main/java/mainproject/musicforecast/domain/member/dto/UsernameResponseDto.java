package mainproject.musicforecast.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameResponseDto {
    private long memberId;
    private String email;
}
