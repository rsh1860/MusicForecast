package mainproject.musicforecast.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mainproject.musicforecast.domain.question.Question;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Getter
@AllArgsConstructor
public class MemberPostDto {
    @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호 양식에 부합하지 않습니다.")
    private String password;
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String nickname;
    @NotBlank(message = "답변을 작성해 주세요.")
    private String auth_answer;
    private long questionNumber;
    private long birthdate;
}
