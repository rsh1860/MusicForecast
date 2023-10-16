package mainproject.musicforecast.domain.member.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {
    //관리자용 권한 목록 생성
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
    //일반 사용 권한 목록
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    public List<GrantedAuthority> createAuthorities(String email) {

        return USER_ROLES;
    }

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return authorities;
    }

    public List<String> createRoles(String email) {
        // 이메일이 관리자 이메일인지 확인하려면 파라미터로 이메일 받아서 관리자 이메일이랑 비교해야함.
        // 하지만 등록해둔 관리자 이메일이 없기 때문에 굳이 email을 사용하지 않는다.

        return USER_ROLES_STRING;
    }
}
