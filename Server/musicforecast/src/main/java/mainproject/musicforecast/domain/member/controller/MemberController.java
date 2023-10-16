package mainproject.musicforecast.domain.member.controller;

import mainproject.musicforecast.domain.member.dto.MemberPatchDto;
import mainproject.musicforecast.domain.member.dto.MemberPostDto;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.mapper.MemberMapper;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.playlist.Utils;
import mainproject.musicforecast.domain.playlist.controller.PlaylistController;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.playlist.mapper.PlaylistMapper;
import mainproject.musicforecast.domain.playlist.service.PlaylistService;
import mainproject.musicforecast.domain.post.entity.Post;
import mainproject.musicforecast.domain.provider.Provider;
import mainproject.musicforecast.domain.provider.ProviderRepository;
import mainproject.musicforecast.global.exception.response.ErrorResponse;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final ProviderRepository providerRepository;
    private final PlaylistService playlistService;
    private final PlaylistMapper playlistMapper;
    public MemberController(MemberService memberService, MemberMapper mapper, ProviderRepository providerRepository,
                            PlaylistService playlistService,
                            PlaylistMapper playlistMapper) {
        this.memberService = memberService;
        this.mapper = mapper;
        this.providerRepository = providerRepository;
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
    }

    //회원가입
    @PostMapping ("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.memberPostDtoToMember(memberPostDto);

        Provider provider = providerRepository.findByProviderName("MusicForecast");

        member.setProvider(provider);

        Member response = memberService.createMember(member);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
    }
    //회원정보 수정
    @PatchMapping("/my_page/{memberId}")
    public ResponseEntity patchMember(@PathVariable("memberId") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto,
                                      @AuthenticationPrincipal Member user) {

        memberPatchDto.setMemberId(memberId);

        Member member = mapper.memberPatchDtoToMember(memberPatchDto);

        Member response = memberService.updateMember(member, user);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
    }
    //회원 자기소개글 조회 기능
    @GetMapping("/my_page/intro")
    public ResponseEntity getMemberIntro(@AuthenticationPrincipal Member user){

        Member response = memberService.findMemberIntro(user.getMemberId());

        return new ResponseEntity<>(mapper.memberToMemberIntroResponseDto(response), HttpStatus.OK);
    }

    //회원 게시글 목록 조회 기능
    @GetMapping("/my_page/post")
    public ResponseEntity getMemberPost(@AuthenticationPrincipal Member user) {

        List<Post> response = memberService.findMemberPost(user);

        return new ResponseEntity<>(mapper.memberToMemberPostResponseDto(user, response), HttpStatus.OK);
    }

    //다른 유저의 자기소개 글 조회
    @GetMapping("/other/intro/{memberId}")
    public ResponseEntity getOtherIntro(@PathVariable("memberId") long memberId) {

        Member response = memberService.findMemberIntro(memberId);

        return new ResponseEntity<>(mapper.memberToMemberIntroResponseDto(response), HttpStatus.OK);
    }

    @GetMapping("/other/post/{memberId}")
    public ResponseEntity getOtherPost(@PathVariable("memberId") long memberId) {

        Member user = memberService.findMember(memberId);

        List<Post> response = memberService.findMemberPost(user);

        return new ResponseEntity<>(mapper.memberToMemberPostResponseDto(user, response), HttpStatus.OK);
    }

    @GetMapping("/other/playlist/{memberId}")
    public ResponseEntity getOtherPlaylist(@RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @PathVariable("memberId") long memberId) {
        Page<Playlist> playlistPage = playlistService.findMyPlaylists(page - 1, size, memberId);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(
                new Utils.MultiResponseDto<>(playlistMapper.playlistToPlaylistResponseDtos(playlists), playlistPage), HttpStatus.OK
        );

    }
    //회원 탈퇴 기능
    @DeleteMapping("/delete")
    public ResponseEntity deleteMember(@AuthenticationPrincipal Member user) {

        memberService.deleteMember(user);
//        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
