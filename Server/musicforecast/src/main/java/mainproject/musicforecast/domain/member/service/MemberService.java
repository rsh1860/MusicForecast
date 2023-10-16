package mainproject.musicforecast.domain.member.service;

import mainproject.musicforecast.domain.member.auth.utils.CustomAuthorityUtils;
import mainproject.musicforecast.domain.member.dto.MemberResponseDto;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.member.utils.RandomNumberGenerator;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.post.repository.PostRepository;
import mainproject.musicforecast.domain.provider.Provider;
import mainproject.musicforecast.domain.provider.ProviderRepository;
import mainproject.musicforecast.domain.question.Question;
import mainproject.musicforecast.domain.question.QuestionService;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final ProviderRepository providerRepository;
    private final PostRepository postRepository;
    private final QuestionService questionService;
    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         CustomAuthorityUtils customAuthorityUtils,
                         ProviderRepository providerRepository,
                         PostRepository postRepository,
                         QuestionService questionService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.customAuthorityUtils = customAuthorityUtils;
        this.providerRepository = providerRepository;
        this.postRepository = postRepository;
        this.questionService = questionService;
    }
    public Member createMember(Member member) {

        Provider provider = member.getProvider();

        if (provider.getProviderName().equals("MusicForecast")){

            // 웹 서비스 내에 중복 메일 확인
            verifyExistsEmail(member.getEmail());

            String encryptedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encryptedPassword);

            List<String> roles = customAuthorityUtils.createRoles(member.getEmail());
            member.setRoles(roles);

            Question question = questionService.findVerifiedQuestion(member.getQuestionNumber());

            member.setQuestion(question);
        } else {
            List<String> roles = customAuthorityUtils.createRoles(member.getEmail());
            member.setRoles(roles);
        }

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member, Member user) {
        //수정하려는 멤버가 가입된 회원인지 확인
        Member findMember = findVerifiedMember(member.getMemberId());

        //수정하려는 멤버와 로그인된 멤버가 같은지 확인, 다를 경우 권한 없음
        if(findMember.getMemberId().equals(user.getMemberId())){
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        Optional.ofNullable(member.getIntro())
                .ifPresent(intro -> findMember.setIntro(intro));
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> findMember.setNickname(nickname));
        Optional.ofNullable(member.getImage())
                .ifPresent(image -> findMember.setImage(image));

        return memberRepository.save(findMember);
    }
    @Transactional(readOnly = true)
    public Member findMemberIntro(long memberId) {

        Member findMember = findVerifiedMember(memberId);

        return findMember;
    }

    public List<Post> findMemberPost(Member user) {
        return postRepository.findAllByMember(user);
    }

    public void deleteMember(Member user) {

//        //회원 존재 여부 확인
//        Member findMember = findVerifiedMember(memberId);

//        //본인이 맞는지 확인
//        if(findMember.getMemberId() != user.getMemberId()){
//            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
//        }

        //TODO 지금은 완전 삭제라 재가입 가능, 회원 상태를 만든다면 탈퇴계정인걸 알면 같은 이메일로 재가입 불가

        String randomString = RandomNumberGenerator.generateRandomString(20);
        user.setEmail(randomString + "@email.com");
        user.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        user.setRoles(null);
        user.setNickname(null);

        memberRepository.save(user);
//        memberRepository.delete(findMember);
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

//        if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_QUIT))
//            throw new BusinessLogicException(ExceptionCode.MEMBER_STATUS_DELETE);

        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            Member findMember = member.get();
            if(findMember.getProvider().getProviderName().equals("Google") || findMember.getProvider().getProviderName().equals("KaKao")){
                // 중복된 메일이 있을 수 있지만 가입한 루트가 구글이나 카카오면 회원가입 가능
            } else {
                throw new BusinessLogicException(ExceptionCode.MEMBER_IS_EXIST);
            }
        }
    }
    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member findExistsEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }
}
