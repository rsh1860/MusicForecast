package mainproject.musicforecast.domain.postLike.repository;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByMemberAndPost(Member member, Post post);
}
