package mainproject.musicforecast.domain.recommend.admin;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    default List<AdminResponseDto> adminToAdminResponseDtos(List<AdminSuggest> adminSuggests) {

        List<AdminResponseDto> responses = adminSuggests.stream().map(admin -> AdminResponseDto.builder()
                                                                        .adminId(admin.getAdminId())
                                                                        .thumbnailLink(admin.getThumbnailLink())
                                                                        .youtubeLink(admin.getYoutubeLink())
                                                                        .artist(admin.getArtist())
                                                                        .title(admin.getTitle()).build())
                                                                        .collect(Collectors.toList());

        return responses;
    }
}
