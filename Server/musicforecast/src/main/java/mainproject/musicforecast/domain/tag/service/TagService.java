package mainproject.musicforecast.domain.tag.service;

import mainproject.musicforecast.domain.tag.entity.Tag;
import mainproject.musicforecast.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

//    public Tag getTag(String tagName) {
//        Optional<Tag> tag = tagRepository.findByTagName(tagName);
//        if (tag.isPresent()) return tag.get();
//        return null;
//    }

    public List<Tag> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }
}
