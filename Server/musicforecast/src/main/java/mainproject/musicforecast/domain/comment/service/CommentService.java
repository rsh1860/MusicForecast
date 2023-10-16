package mainproject.musicforecast.domain.comment.service;

import mainproject.musicforecast.domain.comment.entity.Comment;
import mainproject.musicforecast.domain.comment.repository.CommentRepository;
import mainproject.musicforecast.global.exception.ExceptionCode;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.post.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private MemberService memberService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, MemberService memberService, PostService postService) {
        this.commentRepository = commentRepository;
        this.memberService = memberService;
        this.postService = postService;
    }
    // 답변 생성 service
    public Comment createComment(Comment comment, Member member) {

        Post post = postService.findVerifyPost(comment.getPostId());

        comment.setPost(post);
        comment.setMember(member);
        comment.setNickname(member.getNickname());

        return commentRepository.save(comment);
    }
    // 답변 수정 service
    public Comment updateComment(Comment comment, Member member) {
        // 존재하는 답변인지 찾은 후 업데이트
        Comment findComment = findVerifiedComment(comment.getCommentId());
        // 답변을 작성한 사람과 수정하려는 사람이 일치하는지 확인
        if(!findComment.getMember().getMemberId().equals(member.getMemberId())){
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        Optional.ofNullable(comment.getText())
                .ifPresent(text -> findComment.setText(text));

        return commentRepository.save(findComment);
    }
    // 답변 조회 service
    public Comment findComment(Long commentId) {

        return findVerifiedComment(commentId);
    }
    // 모든 답변 조회 service
    public List<Comment> findComments() {
        return commentRepository.findAll();
    }
    // 답변 삭제 service
    public void deleteComment(Long commentId, Member member) {
        Comment comment = findVerifiedComment(commentId);

        if(!comment.getMember().getMemberId().equals(member.getMemberId())){
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        commentRepository.delete(comment);
    }

    //존재하는 답변인지 확인
    public Comment findVerifiedComment(Long commentId) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}