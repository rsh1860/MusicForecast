package mainproject.musicforecast.domain.member.controller;

import mainproject.musicforecast.domain.member.dto.FindQuestionDto;
import mainproject.musicforecast.domain.member.dto.FindUsernameDto;
import mainproject.musicforecast.domain.member.dto.UpdatePwDto;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.mapper.MemberMapper;
import mainproject.musicforecast.domain.member.service.FindMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/find")
public class FindMemberController {
    private final MemberMapper mapper;
    private final FindMemberService findMemberService;
    public FindMemberController(MemberMapper mapper,
                                FindMemberService findMemberService) {
        this.mapper = mapper;
        this.findMemberService = findMemberService;
    }

//    @GetMapping("/question")
//    public ResponseEntity findQuestion(@RequestParam("nickname") String nickname,
//                                       @RequestParam("birthdate") long birthdate) {
//
//        Member response = findMemberService.findQuestion(nickname, birthdate);
//
//        return new ResponseEntity<>(mapper.memberToQuestionResponseDto(response), HttpStatus.OK);
//    }

    @GetMapping("/username")
    public ResponseEntity findUsername(@RequestParam("questionNumber") long questionNumber,
                                       @RequestParam("auth_answer") String authAnswer,
                                       @RequestParam("nickname") String nickname,
                                       @RequestParam("birthdate") long birthdate) {

        Member response = findMemberService.findUsername(nickname, authAnswer, questionNumber, birthdate);

        return new ResponseEntity<>(mapper.memberToUsernameResponseDto(response), HttpStatus.OK);

    }

    @PatchMapping("/pw")
    public ResponseEntity findPassword(@RequestBody UpdatePwDto updatePwDto) {

        Member member = mapper.UpdatePwDtoToMember(updatePwDto);

        findMemberService.updatePw(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping("/username")
//    public ResponseEntity findId(@RequestBody FindQuestionDto findMemberDto) {
//
//        Member member = mapper.findMemberDto(findMemberDto);
//
//        Member response = findMemberService.findId(member);
//
//        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
//    }
//    @PatchMapping("/pw")
//    public ResponseEntity searchPw(@RequestBody FindMemberDto findMemberDto) {
//
//        Member member = mapper.findMemberDto(findMemberDto);
//
//        Member response = findMemberService.searchPw(member);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity findQuestion(@PathVariable("email") String email) {
//        Member member = findMemberService.findQuestion(email);
//        return new ResponseEntity<>(mapper.memberToFindMemberDto(member), HttpStatus.OK);
//    }
}
