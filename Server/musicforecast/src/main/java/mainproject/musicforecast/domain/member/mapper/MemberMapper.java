package mainproject.musicforecast.domain.member.mapper;

import mainproject.musicforecast.domain.member.dto.*;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.post.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(Member member);
    Member findQuestionDtoToMember(FindQuestionDto findQuestionDto);
    QuestionResponseDto memberToQuestionResponseDto(Member member);
    Member findUsernameDtoToMember(FindUsernameDto findUsernameDto);
    Member UpdatePwDtoToMember(UpdatePwDto updatePwDto);

    UsernameResponseDto memberToUsernameResponseDto(Member member);
    MemberIntroResponseDto memberToMemberIntroResponseDto(Member member);


    default MemberPostResponseDto memberToMemberPostResponseDto(Member member, List<Post> posts) {
        MemberPostResponseDto response = MemberPostResponseDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .build();

        List<MemberPostDetailResponseDto> postResponseDtos = posts.stream()
                .map(post -> MemberPostDetailResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .text(post.getText())
                        .likeCount(post.getLikeCount())
                        .viewCount(post.getViewCount())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        response.setPost(postResponseDtos);

        return response;
    }
}
