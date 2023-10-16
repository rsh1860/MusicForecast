package mainproject.musicforecast.domain.mainpage.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@Service
public class MainpageService {

    private String getAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public ResponseEntity redirectPage(HttpServletRequest request, String beforeLoginPath, String afterLoginPath) {
        HttpHeaders headers = new HttpHeaders();

        // 로그인 하기 전
        if (Objects.equals(getAuthorization(request), null)) {
            headers.setLocation(URI.create(beforeLoginPath));
            return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
        }
        // 로그인 한 후
        headers.setLocation(URI.create(afterLoginPath));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    public ResponseEntity redirectPageByChoice(String redirectPath) {
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(redirectPath));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
