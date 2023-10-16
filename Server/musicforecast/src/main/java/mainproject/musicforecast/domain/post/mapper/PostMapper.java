package mainproject.musicforecast.domain.post.mapper;

import mainproject.musicforecast.domain.comment.mapper.CommentMapper;
import mainproject.musicforecast.domain.member.dto.MemberResponseDto;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.mapper.MemberMapper;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.post.dto.PostPatchDto;
import mainproject.musicforecast.domain.post.dto.PostPostDto;
import mainproject.musicforecast.domain.post.dto.PostResponseDto;
import mainproject.musicforecast.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Service
public interface PostMapper {
    List<PostResponseDto> postsToPostResponses(List<Post> posts);
    List<PostResponseDto> postsToPostResponseDtos(List<Post> posts);

    default Post postPostToPost(MemberService memberService, PostPostDto postPostDto) {
        if ( postPostDto == null ) {
            return null;
        }

        Post post = new Post();
        Member member = new Member();
        member.setMemberId(postPostDto.getMemberId());

        post.setTitle( postPostDto.getTitle() );
        post.setText( postPostDto.getText() );
        post.setMember(memberService.findMember(member.getMemberId()));
        post.setCreatedAt(LocalDateTime.now());

        if (postPostDto.getPlaylistId() != null) {
            Playlist playlist = new Playlist();
            playlist.setPlaylistId(postPostDto.getPlaylistId());
            post.setPlaylist(playlist);
        }
        return post;
    }

    default Post postPatchDtoToPost(PostPatchDto requestBody) {
        Post post = new Post();

        post.setPostId(requestBody.getPostId());
        //post.setVoteCount(requestBody.getVoteCount());
        post.setTitle(requestBody.getTitle());
        post.setText(requestBody.getText());

        if (requestBody.getPlaylistId() != null) {
            Playlist playlist = new Playlist();
            playlist.setPlaylistId(requestBody.getPlaylistId());
            post.setPlaylist(playlist);
        }

        return post;
    }
//
//    default Post postVoteToPost(PostVoteDto postVoteDto) {
//        if ( postVoteDto == null ) {
//            return null;
//        }
//
//        Post post = new Post();
//        post.setPostId( postVoteDto.getPostId() );
//
//        return post;
//    }


    default PostResponseDto postToPostResponse(MemberMapper memberMapper, Post post, CommentMapper commentMapper, boolean userHasLiked) {
        if ( post == null ) {
            return null;
        }

        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setPostId( post.getPostId() );
        postResponseDto.setTitle( post.getTitle() );
        postResponseDto.setText( post.getText() );
        postResponseDto.setComments(post.getComments().stream()
                .map(comment -> commentMapper.CommentToCommentResponseDto(comment)).collect(Collectors.toList()));
        //postResponseDto.setVoteCount( post.getVoteCount() );
        postResponseDto.setLikeCount( post.getLikeCount() );
        postResponseDto.setViewCount(post.getViewCount());
        postResponseDto.setCreatedAt(post.getCreatedAt());


        postResponseDto.setHasLiked(userHasLiked);


        Member member = post.getMember();
        postResponseDto.setMember(memberMapper.memberToMemberResponseDto(member));

        Playlist playlist = post.getPlaylist();
        if(playlist != null) {
            postResponseDto.setPlaylistId(playlist.getPlaylistId());
            postResponseDto.setPlaylistTitle(playlist.getTitle());
        }
        return postResponseDto;
    }

    default PostResponseDto postToPostResponse(MemberMapper memberMapper, Post post, CommentMapper commentMapper) {
        if ( post == null ) {
            return null;
        }
        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setPostId( post.getPostId() );
        postResponseDto.setTitle( post.getTitle() );
        postResponseDto.setText( post.getText() );
        postResponseDto.setComments(post.getComments().stream()
                .map(comment -> commentMapper.CommentToCommentResponseDto(comment)).collect(Collectors.toList()));
        //postResponseDto.setVoteCount( post.getVoteCount() );
        postResponseDto.setLikeCount( post.getLikeCount() );
        postResponseDto.setViewCount(post.getViewCount());
        postResponseDto.setCreatedAt(post.getCreatedAt());

        Member member = post.getMember();
        postResponseDto.setMember(memberMapper.memberToMemberResponseDto(member));

        Playlist playlist = post.getPlaylist();
        if(playlist != null) {
            postResponseDto.setPlaylistId(playlist.getPlaylistId());
            postResponseDto.setPlaylistTitle(playlist.getTitle());
        }
        return postResponseDto;
    }

    default MemberResponseDto memberToResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDto response = new MemberResponseDto();

        response.setMemberId( member.getMemberId() );
        response.setNickname( member.getNickname() );

        return response;
    }


    default PostResponseDto postToPostResponseDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setMember( memberToResponse( post.getMember() ) );
        postResponseDto.setPostId( post.getPostId() );
        postResponseDto.setTitle( post.getTitle() );
        postResponseDto.setText( post.getText() );
        //postResponseDto.setVoteCount( post.getVoteCount() );
        postResponseDto.setLikeCount( post.getLikeCount() );
        postResponseDto.setViewCount( post.getViewCount());
        postResponseDto.setCreatedAt( post.getCreatedAt() );

        Playlist playlist = post.getPlaylist();
        if(playlist != null) {
            postResponseDto.setPlaylistId(playlist.getPlaylistId());
            postResponseDto.setPlaylistTitle(playlist.getTitle());
        }
        return postResponseDto;
    }
}