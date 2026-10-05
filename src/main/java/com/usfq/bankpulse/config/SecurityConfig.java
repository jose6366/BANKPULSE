package com.usfq.bankpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean UserDetailsService users(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
      User.withUsername("demo").password(encoder.encode("demo123")).roles("CLIENT").build(),
      User.withUsername("operator").password(encoder.encode("operator123")).roles("OPERATOR").build()
    );
  }

  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(a -> a
        .requestMatchers("/health", "/actuator/health", "/css/**", "/error").permitAll()
        .requestMatchers("/api/accounts/**", "/api/payments/**", "/payments/**").hasRole("CLIENT")
        .requestMatchers("/").hasAnyRole("CLIENT", "OPERATOR")
        .anyRequest().authenticated())
      .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
      .httpBasic(Customizer.withDefaults())
      .formLogin(f -> f.defaultSuccessUrl("/", true))
      .logout(l -> l.logoutSuccessUrl("/login?logout"));
    return http.build();
  }
}
