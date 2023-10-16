package mainproject.musicforecast.domain.member.repository;

import mainproject.musicforecast.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Member findByMemberId(Long MemberId);
    Optional<Member> findBySub(BigInteger sub);
    Optional<Member> findByKakaoId(BigInteger id);
    Optional<Member> findByNicknameAndBirthdate(String nickname, long birthdate);
    Optional<Member> findByNicknameAndAuthAnswerAndQuestionNumberAndBirthdate(String nickname, String authAnswer, long QuestionNumber, long birthdate);
}
