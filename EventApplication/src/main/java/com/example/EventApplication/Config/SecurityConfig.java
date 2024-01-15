//package com.example.EventApplication.Config;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//@Slf4j
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    //    public SecurityConfig(UserDetailsService userDetailsService) {
////        this.userDetailsService = userDetailsService;
////    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated())
//                .httpBasic(withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("user").password("{noop}test").authorities("user").build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
////    @Bean
////    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
////        HttpSecurity csrf1 = http
////                .csrf(csrf -> csrf
////                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
////                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
////
////        log.debug("AAAAAAAAADSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSAA" + csrf1.toString());
////
////        return http.build();
////    }
////
////    final class CsrfCookieFilter extends OncePerRequestFilter {
////
////        @Override
////        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////                throws ServletException, IOException, ServletException, IOException {
////            CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
////            // Render the token value to a cookie by causing the deferred token to be loaded
////            String token = csrfToken.getToken();
////            log.info(token);
////            log.debug(token);
////
////            filterChain.doFilter(request, response);
////        }
////    }
////
//////    @Override
//////    protected void configure(HttpSecurity http) throws Exception {
////////        http.csrf().disable()
////////                .authorizeRequests()
////////                .antMatchers("/public/**").permitAll()
////////                .anyRequest().authenticated()
////////                .and()
////////                .httpBasic()
////////                .and()
////////                .addFilterBefore(new CustomBasicAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
//////    }
////
//////    protected void configure(HttpSecurity http) throws Exception {
////////        http.
//////        http
//////                .authorizeRequests().requestMatchers("/").permitAll();
//////
//////    }
////
//////    @Bean
//////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//////        http
//////                .authorizeHttpRequests((authz) -> authz
//////                        .anyRequest().authenticated()
//////                )
//////                .httpBasic(withDefaults());
//////        return http.build();
//////    }
//////
//////    @Bean
//////    public WebSecurityCustomizer webSecurityCustomizer() {
//////        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//////    }
////
////
////
//////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//////
//////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }
//}