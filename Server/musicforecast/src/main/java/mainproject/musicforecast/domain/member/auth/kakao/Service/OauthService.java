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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class OauthService extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProviderRepository providerRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;
    public OauthService(MemberService memberService,
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
    public MultiValueMap createGoogleUser(String token) {
        String reqURL = "https://www.googleapis.com/oauth2/v3/userinfo";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection)  url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("response Code : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String stringSub = element.getAsJsonObject().get("sub").getAsString();
            String email = element.getAsJsonObject().get("email").getAsString();

            br.close();

            int length = stringSub.length();

            if(length > 17) {
                stringSub = stringSub.substring(0, 17);
            }

            BigInteger sub = new BigInteger(stringSub);

            Optional<Member> optionalMember = memberRepository.findBySub(sub);
//            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            Member member = null;
            String nickname = null;

            if(optionalMember.isPresent()) {
                member = optionalMember.get();
            } else {

                int atIndex = email.indexOf("@"); // "@" 기호의 위치를 찾습니다.

                if (atIndex != -1) {
                    nickname = email.substring(0, atIndex); // "@" 기호 이전까지의 부분을 추출합니다.
                } else {
                    nickname = "닉네임을 입력해주세요.";
                }
                member = saveMember(sub, email, nickname, "Google");
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

    public MultiValueMap createKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

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
