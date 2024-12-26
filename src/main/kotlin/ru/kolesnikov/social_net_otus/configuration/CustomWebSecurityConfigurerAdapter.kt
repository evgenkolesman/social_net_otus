package ru.kolesnikov.social_net_otus.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ott.InMemoryOneTimeTokenService
import org.springframework.security.authentication.ott.OneTimeTokenService
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class CustomWebSecurityConfigurerAdapter {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
//                    .requestMatchers("/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/logout").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic { }
            .csrf { a -> a.disable() }
            .oneTimeTokenLogin { }

        return http.build()
    }

    @Bean
    fun userDetailsService(dataSource: DataSource): UserDetailsManager {
        val jdbcUserDetailsManager = JdbcUserDetailsManager(dataSource)
//        jdbcUserDetailsManager.s(
//            """
//            CREATE TABLE  IF NOT EXISTS users
//    (
//        username text,
//         password text,
//         enabled boolean
//    );
//        """.trimIndent()
//        )
        return jdbcUserDetailsManager
    }

    @Bean
    fun oneTimeTokenService(): OneTimeTokenService {
        return InMemoryOneTimeTokenService()
    }

    @Bean
    fun oneTimeTokenGenerationSuccessHandler(): OneTimeTokenGenerationSuccessHandler {
        return RedirectOneTimeTokenGenerationSuccessHandler("/")
    }
//
//    private fun passwordEncoder(): PasswordEncoder {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
//    }


}