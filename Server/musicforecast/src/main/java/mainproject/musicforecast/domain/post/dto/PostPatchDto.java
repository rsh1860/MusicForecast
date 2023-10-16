package mainproject.musicforecast.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostPatchDto {
    private Long postId;
    private String title;
    private String text;
    private Long playlistId;
}