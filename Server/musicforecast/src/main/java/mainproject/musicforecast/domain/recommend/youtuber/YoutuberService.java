package mainproject.musicforecast.domain.recommend.youtuber;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutuberService {

    private final YoutuberRepository youtuberRepository;

    public YoutuberService(YoutuberRepository youtuberRepository) {
        this.youtuberRepository = youtuberRepository;
    }

    public List<Youtuber> getAllYoutuber() {
        return youtuberRepository.findAll();
    }
}
