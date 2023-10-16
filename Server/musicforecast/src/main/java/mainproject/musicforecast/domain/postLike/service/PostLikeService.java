package mainproject.musicforecast.domain.postLike.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.post.repository.PostRepository;
import mainproject.musicforecast.domain.postLike.entity.PostLike;
import mainproject.musicforecast.domain.postLike.repository.PostLikeRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public void likePost(long postId, PostLike.LikeType likeType, Member user) {
        Member member = memberRepository.findById(user.getMemberId())
                .orElseThrow(() -> new NullPointerException());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException());

        if (user.getMemberId() == post.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }

        PostLike existPostLike = postLikeRepository.findByMemberAndPost(member, post);

        int totalLikeCount = post.getLikeCount();

        if (existPostLike != null) {
            totalLikeCount += (likeType == PostLike.LikeType.Like)? -1 : 1;
            postLikeRepository.delete(existPostLike);
        } else {
            totalLikeCount += (likeType == PostLike.LikeType.Like)? 1 : -1;
            PostLike postLike = PostLike.builder()
                    .type(likeType)
                    .member(member)
                    .post(post)
                    .build();

            postLikeRepository.save(postLike);
        }

        post.setLikeCount(totalLikeCount);
        postRepository.save(post);
    }

    public boolean hasLikedPost(long postId, Member user) {
        Member member = memberRepository.findById(user.getMemberId())
                .orElseThrow(() -> new NullPointerException());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException());

        PostLike existPostLike = postLikeRepository.findByMemberAndPost(member, post);

        return existPostLike != null;
    }
}