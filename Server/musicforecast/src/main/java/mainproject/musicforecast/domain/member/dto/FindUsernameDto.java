package mainproject.musicforecast.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUsernameDto {
    private long questionNumber;
    private String authAnswer;
}
