package mainproject.musicforecast.config;

import mainproject.musicforecast.domain.member.auth.filter.JwtAuthenticationFilter;
import mainproject.musicforecast.domain.member.auth.filter.JwtVerificationFilter;
import mainproject.musicforecast.domain.member.auth.handler.*;
import mainproject.musicforecast.domain.member.auth.jwt.JwtTokenizer;
import mainproject.musicforecast.domain.member.auth.utils.CustomAuthorityUtils;
import mainproject.musicforecast.domain.member.repository.MemberRepository;
import mainproject.musicforecast.domain.member.service.MemberService;
import mainproject.musicforecast.domain.provider.ProviderRepository;
import mainproject.musicforecast.domain.member.auth.handler.OAuth2MemberSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final ProviderRepository providerRepository;
    private final MemberRepository memberRepository;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 @Lazy MemberService memberService,
                                 @Lazy ProviderRepository providerRepository,
                                 @Lazy MemberRepository memberRepository
                                 ) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberService = memberService;
        this.providerRepository = providerRepository;
        this.memberRepository = memberRepository;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                //.cors(withDefaults())
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .apply(new CustomOauthFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/members/**").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/oauth/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/oauth/**").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/find/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/find/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/playlist/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/playlist/my/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/playlist/**").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/playlist/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/playlist/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/song/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/song/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/song/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/weather/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/mubti/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/youtuber/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/admin-suggest/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/tag/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/posts/**").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/posts/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/posts/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/comments/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/comments/**").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/comments/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/comments/**").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, memberService, providerRepository, memberRepository)));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://musicforecast.s3-website.ap-northeast-2.amazonaws.com");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(List.of("Authorization", "Refresh", "memberId"));

        //configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, memberService);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }


    }

    public class CustomOauthFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, memberService);

            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
