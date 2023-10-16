package mainproject.musicforecast.domain.post.repository;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);
    List<Post> findAllByMember(Member member);

    List<Post> findByTitleContainingIgnoreCase(String keyword);
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    List<Post> findAllByOrderByLikeCountDesc();

    List<Post> findAllByOrderByViewCountDesc();

}