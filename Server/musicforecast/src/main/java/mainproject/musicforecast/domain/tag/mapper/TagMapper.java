package mainproject.musicforecast.domain.tag.mapper;

import mainproject.musicforecast.domain.tag.dto.TagDto;
import mainproject.musicforecast.domain.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    default TagDto.TagResponse TagToTagResponseDto(Tag tag) {
        TagDto.TagResponse response = TagDto.TagResponse.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getTagName()).build();
        return response;
    }

    List<TagDto.TagResponse> TagToTagResponseDtos(List<Tag> tags);
}
