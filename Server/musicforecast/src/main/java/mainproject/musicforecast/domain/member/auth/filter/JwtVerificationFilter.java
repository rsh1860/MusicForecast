package mainproject.musicforecast.domain.member.auth.filter;


import lombok.extern.slf4j.Slf4j;
import mainproject.musicforecast.domain.member.auth.jwt.JwtTokenizer;
import mainproject.musicforecast.domain.member.auth.utils.CustomAuthorityUtils;
import mainproject.musicforecast.domain.member.entity.Member;
import mainproject.musicforecast.domain.member.service.MemberService;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
//로그인 또는 OAuth2 인증에 성공하면 Authorization header에 실어 보내는 Access Token에 대한 검증을 수행하는 Filter
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;

    // (2)
    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 MemberService memberService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // (6)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");  // (6-1)

        return authorization == null || !authorization.startsWith("Bearer");  // (6-2)
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // (3-1)
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // (3-2)
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // (3-3)

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        int memberId = (int) claims.get("memberId");   //수정
        Member user = memberService.findMember(memberId);   //수정
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));  // (4-2)
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);  // (4-3)
        SecurityContextHolder.getContext().setAuthentication(authentication); // (4-4)
    }
}
