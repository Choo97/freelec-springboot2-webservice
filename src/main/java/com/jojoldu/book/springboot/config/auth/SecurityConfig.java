package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //SpringSecurity 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() //h2-console을 위한 위 옵션들 비활성화
                .and().authorizeRequests() // URL 또는 HTTP 메서드별로 관한관리를 해주기 위한 시작점, 그래야 antMatchers를 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 위에 해당하는 URL들은 전체 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER권한을 가진 유저만 가능하도록 함
                .anyRequest().authenticated() // 다른 모든 경로(antRequest)는 인증된 사용자만(authenticated) 접근 가능하도록 함
                .and().logout().logoutSuccessUrl("/") // 로그아웃 성공시 "/" 주소로 이동
                .and().oauth2Login() // auth2 로그인 기능에 대한 설정 진입점
                .userInfoEndpoint() // auth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                .userService(customOauth2UserService); // 소셜 로그인 성공시 후속 조치(추가 기능)를 진행할 구현체 등록
    }
}
