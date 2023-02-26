package resa.mario.empleadodepartamentospring2.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import resa.mario.empleadodepartamentospring2.config.security.jwt.JwtAuthenticationFilter
import resa.mario.empleadodepartamentospring2.config.security.jwt.JwtAuthorizationFilter
import resa.mario.empleadodepartamentospring2.config.security.jwt.JwtTokenUtils
import resa.mario.empleadodepartamentospring2.services.usuario.UsuarioServiceImpl

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig
@Autowired constructor(
    private val service: UsuarioServiceImpl,
    private val jwtTokenUtils: JwtTokenUtils
) {
    @Bean
    fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(service)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)

        http
            .csrf()
            .disable()
            .exceptionHandling()
            .and()

            // Para token JWT
            .authenticationManager(authenticationManager)

            // No usamos estado de sesion
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            // Aceptamos request del tipo Http
            .authorizeHttpRequests()

            // Spring desplaza a esta ruta todos los errores y excepciones, asi podemos mostrarlas en vez de un Forbidden
            .requestMatchers("/error/**").permitAll()

            // Permitimos el acceso a la API
            .requestMatchers("/api/**").permitAll()

            // Permitimos el acceso sin autenticacion ni verificacion a las siguientes rutas
            .requestMatchers("/usuarios/login", "/usuarios/register").permitAll()

            // Para listar a los usuarios, se requeria un rol de ADMIN
            .requestMatchers("/usuarios/list").hasRole("ADMIN")

            // El resto, se necesitara autenticacion estandar; en este caso, unicamente /me
            .anyRequest().authenticated()

            .and()

            .addFilter(JwtAuthenticationFilter(jwtTokenUtils, authenticationManager)) // Autenticacion
            .addFilter(JwtAuthorizationFilter(jwtTokenUtils, service, authenticationManager)) // Autorizacion

        return http.build()
    }
}