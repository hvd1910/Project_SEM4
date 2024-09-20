package com.example.BackEndSem4.configuration;

import com.example.BackEndSem4.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
@RequiredArgsConstructor

public class WebSecurityConfig {


    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Kích hoạt CORS
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/payment/vn-pay-callback**", apiPrefix),
                                    String.format("%s/contacts", apiPrefix)


                            ).permitAll()
                            .requestMatchers(GET, String.format("%s/roles**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/spcialties/**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/doctors/**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/clinics/**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/schedules/doctor**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/time-slots/specialty/**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/medications/**", apiPrefix)).permitAll()

                            .requestMatchers(GET, String.format("%s/images/view/**", apiPrefix)).permitAll()

//



                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable); // Vô hiệu hóa bảo vệ CSRF cho đơn giản (chỉ khi bạn biết rõ mình đang làm gì)

        return http.build();
    }
}
