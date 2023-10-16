package mainproject.musicforecast.domain.post.controller;

import mainproject.musicforecast.domain.comment.mapper.CommentMapper;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.mapper.MemberMapper;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.post.dto.*;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.post.mapper.PostMapper;
import mainproject.musicforecast.domain.post.repository.PostRepository;
import mainproject.musicforecast.domain.post.service.PostService;
import mainproject.musicforecast.domain.postLike.entity.PostLike;
import mainproject.musicforecast.domain.postLike.service.PostLikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/posts")
@Validated
public class PostController {

    private final PostService postService;
    private final PostMapper mapper;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    private final CommentMapper commentMapper;

    private final PostRepository postRepository;

    private final PostLikeService postLikeService;

    public PostController(PostService postService, PostMapper mapper, MemberService memberService, MemberMapper memberMapper, CommentMapper commentMapper, PostRepository postRepository, PostLikeService postLikeService) {
        this.postService = postService;
        this.mapper = mapper;
        this.memberService = memberService;
        this.memberMapper = memberMapper;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.postLikeService = postLikeService;
    }

    // 글 등록
//    @PostMapping
//    public ResponseEntity postPost(@Valid @RequestBody PostPostDto postPostDto) {
//
//        Post post = postService.createPost(mapper.postPostToPost(memberService, postPostDto));
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.postToPostResponse(memberMapper, post, commentMapper))
//                , HttpStatus.CREATED);
//    }

    //글 등록
    @PostMapping
    public ResponseEntity postPost(
            @Valid @RequestBody PostPostDto postPostDto
    ) {
        // PostPostDto로부터 Post 엔티티를 생성하고 저장합니다.
        Post post = mapper.postPostToPost(memberService, postPostDto);
        post = postService.createPost(post, postPostDto.getPlaylistId());


        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.postToPostResponse(memberMapper, post, commentMapper)),
                HttpStatus.CREATED
        );
    }
    // 글 수정
    @PatchMapping("/{post-id}")
    public ResponseEntity patchPost(@PathVariable("post-id") @Positive long postId,
                                    @Valid @RequestBody PostPatchDto requestBody,
                                    @AuthenticationPrincipal Member member) {
        requestBody.setPostId(postId);

        Post post = postService.updatePost(
                mapper.postPatchDtoToPost(requestBody), requestBody.getPlaylistId(), member);


        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.postToPostResponseDto(post)),
                HttpStatus.OK);
    }

//    // 투표하기 Vote=true +1, 투표 취소하기 Vote=false -1
//    @PatchMapping("/{post-id}/vote")
//    public ResponseEntity patchVote(@PathVariable("post-id") @Positive Long postId,
//                                    @RequestParam Boolean vote,
//                                    @Valid @RequestBody PostVoteDto postVoteDto) {
//        postVoteDto.setPostId(postId);
//
//        Post post = postService.votePost(mapper.postVoteToPost(postVoteDto),
//                vote);
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.postToPostResponse(memberMapper, post, commentMapper))
//                , HttpStatus.OK);
//    }

    // 특정 게시글 조회
    @GetMapping("/{post-id}")
    public ResponseEntity getPost(@PathVariable("post-id") @Positive Long postId, @AuthenticationPrincipal Member member) {

        Member currentUser = memberService.findMember(member.getMemberId());

        Post post = postService.findGetPost(postId);

        boolean userHasLiked = postLikeService.hasLikedPost(postId,currentUser);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.postToPostResponse(memberMapper, post, commentMapper,userHasLiked)),
                HttpStatus.OK);
    }


    // 전체 게시글 조회
//    @GetMapping  // page = 1, size = 10으로 설정
//    public ResponseEntity getPosts(@Positive @RequestParam int page,
//                                   @Positive @RequestParam int size) {
//        Page<Post> pagePosts = postService.findPosts(page - 1, size);
//        List<Post> posts = pagePosts.getContent();
//        List<PostResponseDto> response =
//                posts.stream()
//                        .map(post -> mapper.postToPostResponse(memberMapper, post, commentMapper))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(response, pagePosts),
//                HttpStatus.OK);
//    }

    //전체 게시글 조회(필터링 가능 viewCount, likeCount 순으로 나열 가능)
    @GetMapping
    public ResponseEntity getPosts( @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "postId,desc") String sort) {
        // Parsing sort parameter
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortOrder = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortOrder, sortField));


        // 페이지 및 정렬 정보를 이용하여 게시물 조회
        Page<Post> pagePosts = postService.findPosts(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostResponseDto> response =
                posts.stream()
                        .map(post -> mapper.postToPostResponse(memberMapper, post, commentMapper))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(
                new MultiResponseDto<>(response, pagePosts),
                HttpStatus.OK);
    }
    // 게시글 삭제
    @DeleteMapping("/{post-id}")
    public ResponseEntity deletePost(@PathVariable("post-id") @Positive Long postId,
                                     @AuthenticationPrincipal Member member) {
        postService.deletePost(postId, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 게시글 키워드로 검색
    @GetMapping("/search")
    public ResponseEntity searchPosts(
            @Positive @RequestParam int page,
            @Positive @RequestParam int size,
            @RequestParam String keyword) {
        Page<Post> pagePosts = postService.searchPostsByKeyword(page - 1, size, keyword);
        List<Post> posts = pagePosts.getContent();
        List<PostResponseDto> response = posts.stream()
                .map(post -> mapper.postToPostResponse(memberMapper, post, commentMapper))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new MultiResponseDto<>(response, pagePosts),
                HttpStatus.OK);
    }
//좋아요기능 (table분리해서 다시만듬)
    @PatchMapping("/{post-id}/like")
    public ResponseEntity likePost(@PathVariable("post-id") long postId,
                                       @RequestBody Like postLikeDto,
                                       @AuthenticationPrincipal Member member) {
        postLikeService.likePost(postId, PostLike.LikeType.Like, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    // likeCount로 정렬된 게시물 목록 조회
//    @GetMapping("/sortByLikeCount")
//    public List<Post> getPostsSortedByLikeCount() {
//        return postRepository.findAllByOrderByLikeCountDesc();
//    }
//    @GetMapping("/sortByViewCount")
//    public List<Post> getPostsSortedByViewCount() {
//        return postRepository.findAllByOrderByViewCountDesc();
//    }
}