package mainproject.musicforecast.domain.mainpage.controller;

import mainproject.musicforecast.domain.mainpage.service.MainpageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/main")
public class MainpageController {

    private final MainpageService mainpageService;

    public MainpageController(MainpageService mainpageService) {
        this.mainpageService = mainpageService;
    }

    @GetMapping("/weather")
    public ResponseEntity clickWeather(HttpServletRequest request) {
        return mainpageService.redirectPage(request, "/auth/login", "/weatherRecommend");
    }

    @GetMapping("/mubti")
    public ResponseEntity clickMubti(HttpServletRequest request) {
        return mainpageService.redirectPage(request, "/auth/login", "/mubti");
    }

    @GetMapping("/posts")
    public ResponseEntity clickCommunity(HttpServletRequest request) {
        return mainpageService.redirectPage(request, "/auth/login", "/posts");
    }
}