package com.javaproject.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    /**
     * Enable CORS.
     */
    @Value("${config.cors.enabled}")
    private boolean corsEnabled;

    /**
     * Login page.
     */
    public static final String LOGIN_PAGE = "/api/auth/login";

    /**
     * CorsConfigurationSource.
     */
    private final CorsConfigurationSource corsConfigurationSource;

    /**
     * SecurityConfig constructor.
     *
     * @param corsConfigurationSource service value to autowire
     */
    public SecurityConfig(final CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    /**
     * Maximum sessions number.
     */
    public static final int MAXIMUM_SESSIONS = 5;

    private String[] accessibleToAll() {
        var staticPaths = Stream.of(
                "/api/public/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/h2-console/**",
                "/resources/**",
                "/static/**",
                "/public/**",
                "/favicon.ico",
                LOGIN_PAGE, "/oauth2/**"
        );
        return staticPaths.toList().toArray(new String[0]);
    }

    /**
     * Configures HttpSecurity OAuth2.
     *
     * @param http the HttpSecurity to configure.
     * @return a configured SecurityFilterChain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (enable if needed)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                accessibleToAll()
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(LOGIN_PAGE)
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint(LOGIN_PAGE),
                                request -> true
                        )
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(MAXIMUM_SESSIONS)
                        .expiredUrl(LOGIN_PAGE + "?expired")
                        .sessionRegistry(sessionRegistry())
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource));

//        if (!corsEnabled) {
//            http.cors(AbstractHttpConfigurer::disable);
//        }
        return http.build();
    }

    /**
     * Keep track of active user sessions.
     *
     * @return a SessionRegistry bean.
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * Spring Security listener that helps track session lifecycle events.
     *
     * @return a HttpSessionEventPublisher bean.
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Stores passwords in a hashed format.
     *
     * @return a PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
