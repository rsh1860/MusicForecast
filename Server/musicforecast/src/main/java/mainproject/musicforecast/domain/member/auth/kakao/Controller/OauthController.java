package mainproject.musicforecast.domain.member.auth.kakao.Controller;

import mainproject.musicforecast.domain.member.auth.kakao.Service.KakaoOauthService;
import mainproject.musicforecast.domain.member.auth.kakao.Service.OauthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/oauth")
public class OauthController {
    private final KakaoOauthService kakaoOauthService;
    private final OauthService oauthService;
    public OauthController(KakaoOauthService kakaoOauthService,
                           OauthService oauthService) {
        this.kakaoOauthService = kakaoOauthService;
        this.oauthService = oauthService;
    }
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    @PostMapping("/v2/kakao")
    public ResponseEntity kakaoCallback(@RequestHeader("code") String code) {
        System.out.println(code);

        String token = kakaoOauthService.getKakaoAccessToken(code);
        MultiValueMap map = kakaoOauthService.createKakaoUser(token);

        HttpHeaders tokenHeader = new HttpHeaders(map);

        tokenHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        tokenHeader.setAcceptCharset(List.of(UTF_8));

        return ResponseEntity.ok().headers(tokenHeader).body("");
    }

    @PostMapping("/google")
    public ResponseEntity createGoogleToken(@RequestHeader("Authorization") String token) {

        MultiValueMap map = oauthService.createGoogleUser(token);

        HttpHeaders tokenHeader = new HttpHeaders(map);

        return ResponseEntity.ok().headers(tokenHeader).body("");
    }

    @PostMapping("/kakao")
    public ResponseEntity createKakaoToken(@RequestHeader("Authorization") String token) {

        MultiValueMap map = oauthService.createKakaoUser(token);

        HttpHeaders tokenHeader = new HttpHeaders(map);

        return ResponseEntity.ok().headers(tokenHeader).body("");
    }
}
