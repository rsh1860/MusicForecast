package mainproject.musicforecast.domain.post.service;

import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.repository.PlaylistRepository;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.post.repository.PostRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final PlaylistRepository playlistRepository;

    public PostService(PostRepository postRepository, MemberService memberService, MemberRepository memberRepository, PlaylistRepository playlistRepository) {
        this.postRepository = postRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.playlistRepository = playlistRepository;
    }

//    public Post createPost(Post post) {
//        Member member = memberRepository.findByMemberId(post.getMember().getMemberId());
//        post.setMember(member);
//        return postRepository.save(post);
//    }

    public Post createPost(Post post, Long playlistId) {
        // 게시물 작성에 필요한 로직을 수행합니다.
        // 플레이리스트 ID를 사용하여 해당 플레이리스트를 조회합니다.
        if (playlistId != null) {
            Playlist playlist = playlistRepository.findById(playlistId)
                    .orElseThrow(() -> new EntityNotFoundException("플레이리스트를 찾을 수 없습니다."));

            // 게시물과 플레이리스트를 연결합니다.
            post.setPlaylists(playlist);
        }
        // 게시물을 작성한 회원을 조회하고 연결합니다.
        Member member = memberRepository.findByMemberId(post.getMember().getMemberId());
        post.setMember(member);

        // 게시물을 저장합니다.
        return postRepository.save(post);
    }

    public Post updatePost(Post post, Long newPlaylistId, Member loggedInMember) {
        Post findPost = postRepository.findByPostId(post.getPostId());

        if (newPlaylistId != null){
            Playlist newPlaylist = playlistRepository.findById(newPlaylistId)
                    .orElseThrow(() -> new EntityNotFoundException("플레이리스트를 찾을 수 없습니다."));

            findPost.setPlaylists(newPlaylist);
        } else {
            // newPlaylistId가 null인 경우, 플레이리스트를 삭제하고 연결을 끊습니다.
            findPost.setPlaylist(null);
        }

        if (findPost.getMember().getMemberId().equals(loggedInMember.getMemberId())) {
            Optional.ofNullable(post.getText())
                    .ifPresent(text -> findPost.setText(text));

            return postRepository.save(findPost);
        } else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }
    }
//
//    public Post votePost(Post post, Boolean vote) {
//        Post findPost = postRepository.findByPostId(post.getPostId());
//        if (vote.equals(true)) {
//            findPost.setVoteCount(findPost.getVoteCount() + 1);
//        } else {
//            findPost.setVoteCount(findPost.getVoteCount() - 1);
//        }
//        Post post1 = postRepository.save(findPost);
//        return post1;
//    }

    public Post findPost(Long postId) {
        return findVerifyPost(postId);
    }

    public Post findGetPost(Long postId) {
        Post post = findVerifyPost(postId);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return post;
    }

//    public Page<Post> findPosts(int page, int size) {
//        return postRepository.findAll(PageRequest.of(page, size, Sort.by("postId").descending()));
//    }

    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Post findVerifyPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        return post;
    }

    public Post VerifyPostId(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post findPost = optionalPost.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        return findPost;
    }

    public void deletePost(Long postId, Member loggedInMember) {
        Post post = findVerifyPost(postId);

        if (!post.getMember().getMemberId().equals(loggedInMember.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_PERMISSION_DENIED);
        }
        postRepository.delete(post);
    }

    public Page<Post> searchPostsByKeyword(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

}
