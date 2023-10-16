package mainproject.musicforecast.domain.member.auth.kakao.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mainproject.musicforecast.domain.member.auth.jwt.JwtTokenizer;
import mainproject.musicforecast.domain.member.auth.utils.CustomAuthorityUtils;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.provider.Provider;
import mainproject.musicforecast.domain.provider.ProviderRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sound.midi.MetaMessage;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@Service
public class KakaoOauthService extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProviderRepository providerRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;
    public KakaoOauthService(MemberService memberService,
                             MemberRepository memberRepository,
                             ProviderRepository providerRepository,
                             CustomAuthorityUtils authorityUtils,
                             JwtTokenizer jwtTokenizer) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.providerRepository = providerRepository;
        this.authorityUtils = authorityUtils;
        this.jwtTokenizer = jwtTokenizer;
    }

    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=4c5d1970a764dd126fa0800e2b151812"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://musicforecast.s3-website.ap-northeast-2.amazonaws.com/login"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);


            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public MultiValueMap createKakaoUser(String token){

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            long id = element.getAsJsonObject().get("id").getAsLong();
            BigInteger kakaoId = BigInteger.valueOf(id);

            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            String nickname = "";

            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            br.close();

            Optional<Member> optionalMember = memberRepository.findByKakaoId(kakaoId);
            Member member = null;

            if(optionalMember.isPresent()) {
                member = optionalMember.get();
                nickname = member.getNickname();
            } else {

                int atIndex = email.indexOf("@"); // "@" 기호의 위치를 찾습니다.

                if (atIndex != -1) {
                    nickname = email.substring(0, atIndex); // "@" 기호 이전까지의 부분을 추출합니다.
                } else {
                    nickname = "닉네임을 입력해주세요.";
                }
                member = saveMember(kakaoId, email, nickname, "KaKao");
            }

            List<String> authorities = authorityUtils.createRoles(email);

            String accessToken = delegateAccessToken(member);
            String refreshToken = delegateRefreshToken(member.getEmail());

            map.put("Authorization", Collections.singletonList("Bearer " + accessToken));
            map.put("Refresh", Collections.singletonList(refreshToken));
            map.put("memberId", Collections.singletonList(Long.toString(member.getMemberId())));
            map.put("nickname", Collections.singletonList(nickname));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    private Member saveMember(BigInteger memberNum, String email, String nickname, String providerName) {

        Member savedMember = new Member();
        Provider provider = null;

        savedMember.setEmail(email);
        savedMember.setNickname(nickname);
        savedMember.setIntro("자기소개를 입력해주세요.");
        savedMember.setImage("https://cdn-icons-png.flaticon.com/512/1361/1361876.png");

        //memberNum은 카카오나 구글에서 받은 고유 번호, 카카오는 id, 구글은 sub을 뜻한다
        if(providerName.equals("Google")){
            savedMember.setSub(memberNum);
            provider = providerRepository.findByProviderName("Google");
        } else if(providerName.equals("KaKao")){
            savedMember.setKakaoId(memberNum);
            provider = providerRepository.findByProviderName("KaKao");
        }

        savedMember.setProvider(provider);

        memberService.createMember(savedMember);

        return savedMember;
    }

    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>(); //토큰에 넣고싶은 member 정보
        claims.put("memberId", member.getMemberId());
        claims.put("nickname", member.getNickname());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
