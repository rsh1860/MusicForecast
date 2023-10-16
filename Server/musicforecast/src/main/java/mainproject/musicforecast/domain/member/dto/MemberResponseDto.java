package mainproject.musicforecast.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import mainproject.musicforecast.domain.playlist.entity.Playlist;
import mainproject.musicforecast.domain.post.entity.Post;

import java.util.List;

@Getter
@Setter
public class MemberResponseDto {
    private long memberId;
    private String nickname;
    private String email;
    private String intro;
    private String image;
}
