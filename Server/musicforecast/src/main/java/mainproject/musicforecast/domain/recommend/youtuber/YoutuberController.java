package mainproject.musicforecast.domain.recommend.youtuber;

import mainproject.musicforecast.domain.playlist.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/youtuber")
public class YoutuberController {

    private final YoutuberService youtuberService;
    private final YoutuberMapper mapper;

    public YoutuberController(YoutuberService youtuberService, YoutuberMapper mapper) {
        this.youtuberService = youtuberService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getYoutubers(@RequestParam("q") int num) {
        List<Youtuber> youtubers = youtuberService.getAllYoutuber();

        int size = youtubers.size(); //0 ~ size-1

        Random random = new Random();

        List<Youtuber> results = new ArrayList<>();

        while (results.size() < num) {
            int randomNum = random.nextInt(size);
            if (results.contains(youtubers.get(randomNum))) continue;
            results.add(youtubers.get(randomNum));
        }

        return new ResponseEntity<>(
                new Utils.SingleResponseDto<>(mapper.youtuberToYoutuberResponseDtos(results)), HttpStatus.OK
        );
    }
}
