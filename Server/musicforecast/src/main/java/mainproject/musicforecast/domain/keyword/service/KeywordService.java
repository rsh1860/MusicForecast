package mainproject.musicforecast.domain.keyword.service;

import mainproject.musicforecast.domain.keyword.entity.Keyword;
import mainproject.musicforecast.domain.keyword.repository.KeywordRepository;
import mainproject.musicforecast.domain.song.entity.Song;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Keyword createKeyword(Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    public boolean findKeyword(String keyword) {
        Optional<Keyword> findKeyword = keywordRepository.findBySongKeyword(keyword);
        return findKeyword.isEmpty()? false : true;
    }
}
