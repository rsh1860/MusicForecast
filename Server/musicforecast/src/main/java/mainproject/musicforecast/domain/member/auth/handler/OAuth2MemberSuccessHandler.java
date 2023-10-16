package mainproject.musicforecast.domain.member.auth.handler;

import mainproject.musicforecast.domain.member.auth.jwt.JwtTokenizer;
import mainproject.musicforecast.domain.member.auth.utils.CustomAuthorityUtils;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.provider.Provider;
import mainproject.musicforecast.domain.provider.ProviderRepository;
import mainproject.musicforecast.global.exception.BusinessLogicException;
import mainproject.musicforecast.global.exception.ExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

//OAuth2 인증에 성공하면 호출되는 핸들러
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final ProviderRepository providerRepository;
    private final MemberRepository memberRepository;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      CustomAuthorityUtils authorityUtils,
                                      MemberService memberService,
                                      ProviderRepository providerRepository,
                                      MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberService = memberService;
        this.providerRepository = providerRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();

        String token = authentication.toString();
        System.out.println(token + "\n");
        System.out.println(authentication+ "\n");

        System.out.println(oAuth2User);

        String email = String.valueOf(oAuth2User.getAttributes().get("email")); // (3)
        String nickname = null;

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member = null;

        if(optionalMember.isPresent()) {
            member = optionalMember.get();
        }else {
            //nickname = String.valueOf(oAuth2User.getAttributes().get("name"));
                int atIndex = email.indexOf("@"); // "@" 기호의 위치를 찾습니다.

                if (atIndex != -1) {
                    nickname = email.substring(0, atIndex); // "@" 기호 이전까지의 부분을 추출합니다.
                } else {
                    nickname = "닉네임을 입력해주세요.";
            }
            member = saveMember(email, nickname);
        }

        List<String> authorities = authorityUtils.createRoles(email);

        String accessToken = delegateAccessToken(member, authorities);  // (6-1)
        String refreshToken = delegateRefreshToken(member.getEmail());     // (6-2)

        redirect(request, response, member, authorities, accessToken, refreshToken);  // (6)
    }

    private Member saveMember(String email, String nickname) {

        Member member = new Member();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setImage("https://cdn-icons-png.flaticon.com/512/1361/1361876.png");
        Provider provider = providerRepository.findByProviderName("Google");
        member.setProvider(provider);
        memberService.createMember(member);
        return member;
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member, List<String> authorities, String accessToken, String refreshToken) throws IOException {

        String uri = createURI(accessToken, refreshToken, Long.toString(member.getMemberId()), member.getNickname()).toString();   // (6-3)

        getRedirectStrategy().sendRedirect(request, response, uri);   // (6-4)
    }

    private String delegateAccessToken(Member member, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>(); //토큰에 넣고싶은 member 정보
        claims.put("memberId", member.getMemberId());
        claims.put("roles", authorities);

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }


    private String delegateRefreshToken(String username) { // username == email
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken, String memberId, String nickname) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", "Bearer " + accessToken);
        queryParams.add("refresh_token", refreshToken);
        queryParams.add("memberId", memberId);
        queryParams.add("nickname", nickname);


        //TODO Oauth2 성공 후 리다이렉트 할 주소 넣기
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                //.host("musicforecast.s3-website.ap-northeast-2.amazonaws.com")
                .host("localhost")
                //.port(80)
                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}
