package mainproject.musicforecast.domain.recommend.youtuber;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YoutuberMapper {

    default List<YoutuberResponseDto> youtuberToYoutuberResponseDtos(List<Youtuber> youtubers) {

        List<YoutuberResponseDto> responses = youtubers.stream().map(youtuber -> YoutuberResponseDto.builder()
                                                                            .youtuberId(youtuber.getYoutuberId())
                                                                            .youtuberName(youtuber.getYoutuberName())
                                                                            .youtuberLink(youtuber.getYoutuberLink())
                                                                            .youtuberImage(youtuber.getYoutuberImage()).build())
                                                                            .collect(Collectors.toList());

        return responses;
    }
}
