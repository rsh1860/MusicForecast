package mainproject.musicforecast.domain.member.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindMemberService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public FindMemberService(MemberService memberService,
                             MemberRepository memberRepository,
                             PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member findQuestion(String nickname, long birthdate) {

        Optional<Member> optionalMember = memberRepository.findByNicknameAndBirthdate(nickname, birthdate);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    public Member findUsername(String nickname, String authAnswer, long questionNumber, long birthdate) {

        Optional<Member> optionalMember = memberRepository.findByNicknameAndAuthAnswerAndQuestionNumberAndBirthdate(nickname, authAnswer, questionNumber, birthdate);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    public void updatePw(Member user) {
        Member findMember = memberService.findVerifiedMember(user.getMemberId());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        findMember.setPassword(encryptedPassword);

        memberRepository.save(findMember);
    }
    public Member searchPw(Member user) {
    return user;
    }

    public Member findId(Member user) {
        String nickname = user.getNickname();
        long birthdate = user.getBirthdate();

        Optional<Member> optionalMember = memberRepository.findByNicknameAndBirthdate(nickname, birthdate);

        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if(user.getQuestionNumber() != findMember.getQuestionNumber()) {
            new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        if(user.getAuthAnswer() != findMember.getAuthAnswer()) {
            new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        return findMember;
    }

    public Member findQuestion(String email) {
        Member member = memberService.findExistsEmail(email);

        return member;
    }

}
