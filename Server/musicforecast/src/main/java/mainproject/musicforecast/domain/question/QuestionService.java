package mainproject.musicforecast.domain.question;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }
}
