package com.asrai.joinit.config;

import com.asrai.joinit.user.UserRepository;
import com.asrai.joinit.util.jwt.CustomJsonUsernamePasswordAuthenticationFilter;
import com.asrai.joinit.util.jwt.JwtAuthenticationProcessingFilter;
import com.asrai.joinit.util.jwt.JwtService;
import com.asrai.joinit.util.jwt.LoginFailureHandler;
import com.asrai.joinit.util.jwt.LoginService;
import com.asrai.joinit.util.jwt.LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 보안 사용 X
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
            .and()
            .formLogin().disable() // FormLogin 사용 X
            .httpBasic().disable() // httpBasic 사용 X
            .headers().frameOptions().disable()
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/**").permitAll();

            // 세션 사용하지 않으므로 STATELESS로 설정

//            .and()

            //== URL별 권한 관리 옵션 ==//
//            .authorizeRequests()

            // 접근 가능
//            .dispatcherTypeMatchers(HttpMethod.valueOf("/**")).permitAll() // 회원관련 로직 접근 가능
//            .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
//            .and()
//            //== 소셜 로그인 설정 ==//
//            .oauth2Login()
//            .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
//            .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
//            .userInfoEndpoint()
//            .userService(customOAuth2UserService); // customUserService 설정


        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
//        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
//        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    /**
//     * AuthenticationManager 설정 후 등록
//     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
//     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
//     * UserDetailsService는 커스텀 LoginService로 등록
//     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
//     *
//     */
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(loginService);
//        return new ProviderManager(provider);
//    }
//
//    /**
//     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
//     */
//    @Bean
//    public LoginSuccessHandler loginSuccessHandler() {
//        return new LoginSuccessHandler(jwtService, userRepository);
//    }
//
//    /**
//     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
//     */
//    @Bean
//    public LoginFailureHandler loginFailureHandler() {
//        return new LoginFailureHandler();
//    }
//
//    /**
//     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
//     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
//     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
//     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
//     */
//    @Bean
//    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
//        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
//            = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
//        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
//        return customJsonUsernamePasswordLoginFilter;
//    }
//
//    @Bean
//    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
//        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
//        return jwtAuthenticationFilter;
//    }

}
