package com.example.springjpa.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security에 대한 기본적인 설정 추가
     * 1. Configure Method를 통해 정적 자원들에 대해서는 Security 적용 X
     * 2. configure Method를 통해 로그인을 요구 혹은 요구하지 않음을 설정
     * 3. form 기반의 로그인을 활용하는 경우 Login URL, 로그인 성공시 전달할 URL, 로그인 실패 URL을 설정
     */

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().permitAll()
                .and()
                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable(); // form 기반의 로그인에 대해 비활성화 한다.
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncode() {
        return new BCryptPasswordEncoder();
    }
}
