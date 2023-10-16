package mainproject.musicforecast.domain.recommend.admin;

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
@RequestMapping("admin-suggest")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper mapper;

    public AdminController(AdminService adminService, AdminMapper mapper) {
        this.adminService = adminService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getAdminSuggest(@RequestParam("q") int num) {
        List<AdminSuggest> adminSuggestSuggestion = adminService.getAllSuggestion();

        int size = adminSuggestSuggestion.size(); //0 ~ size-1

        Random random = new Random();

        List<AdminSuggest> results = new ArrayList<>();

        while (results.size() < num) {
            int randomNum = random.nextInt(size);
            if (results.contains(adminSuggestSuggestion.get(randomNum))) continue;
            results.add(adminSuggestSuggestion.get(randomNum));
        }

        return new ResponseEntity<>(
                new Utils.SingleResponseDto<>(mapper.adminToAdminResponseDtos(results)), HttpStatus.OK
        );
    }
}
