package mainproject.musicforecast.domain.tag.controller;

import mainproject.musicforecast.domain.playlist.Utils;
import mainproject.musicforecast.domain.tag.entity.Tag;
import mainproject.musicforecast.domain.tag.mapper.TagMapper;
import mainproject.musicforecast.domain.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final TagMapper mapper;

    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

//    @GetMapping("/{tag-name}")
//    public ResponseEntity getTag(@PathVariable("tag-name") String tagName) {
//        Tag tag = tagService.getTag(tagName);
//        return new ResponseEntity(
//                new Utils.SingleResponseDto<>(mapper.TagToTagResponseDto(tag)), HttpStatus.OK
//        );
//    }

    @GetMapping
    public ResponseEntity getTags() {
        List<Tag> tags = tagService.getTags();
        return new ResponseEntity(
                new Utils.SingleResponseDto<>(mapper.TagToTagResponseDtos(tags)), HttpStatus.OK
        );
    }
}
