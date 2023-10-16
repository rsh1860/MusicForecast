package mainproject.musicforecast.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TagDto {

    @Builder
    @Getter @Setter
    public static class TagResponse {
        private long tagId;
        private String tagName;
    }
}
