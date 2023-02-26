package resa.mario.empleadodepartamentospring2

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import resa.mario.empleadodepartamentospring2.config.db.getUsersInit
import resa.mario.empleadodepartamentospring2.controller.UsuarioController

@SpringBootApplication
@EnableCaching
@EnableR2dbcRepositories
class EmpleadoDepartamentoSpring2Application
@Autowired constructor(
    private val controller: UsuarioController
) : CommandLineRunner {
    override fun run(vararg args: String?): Unit = runBlocking {
        // Datos iniciales
        getUsersInit().forEach {
            controller.createByAdminInitializer(it)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<EmpleadoDepartamentoSpring2Application>(*args)
}

/* -- EXPLICACION

Dependencias que marque en un comienzo:
    - Base de datos [H2]
    - Cache [Spring Cache Abstraction]
    - R2DBC [Spring Data R2DBC]
    - Validation
    - WebSocket
    - WebFlux
    - Spring Security

Dependencias que escribi a mano:
    - Kotlin Serialization
        kotlin("plugin.serialization") version "1.7.20"
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    - Comentar la dependencia no reactiva de H2
    - Logs
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    - JWT
	    implementation("com.auth0:java-jwt:4.2.1")

Guia de construccion:

    -- AVISO -- SOLO SE EXPLICAN LOS PASOS RELACIONADOS CON LOS USUARIOS Y SPRING SECURITY, EL RESTO SE ENCUENTRAN EN LA
    VERSION ANTERIOR/BASICA

    -- AVISO 2 -- Muchas implementaciones no saldran para importar hasta que hayan sido escritas al completo, NO es
    por falta de dependencias en el build.

1. Modelo usuario con los campos base (minimo id, username y password)

2. Generar DTO que necesitemos (Muy recomendable el de login y el de registro)

3. Generar mappers

4. De vuelta al modelo, se le agrega la implementacion UserDetails y se configuran las funciones que arrastra

5. Generamos el repositorio, se debera crear la funcion FindByUsername en la interfaz

6. Generamos el passwordEncoder [EncoderConfig]

7. Realizamos el servicio; se le inyecta por parametro el repositorio y PasswordEncoder; se implementa UserDetailsService

8. Siendo honestos, la parte de seguridad es siempre igual y no hay mucho que tener en cuenta, mas alla del orden:

    - JwtTokenUtils: Configuramos la generacion de tokens y su descodificacion

    - JwtAuthenticationFilter: Configuramos la autenticacion y generacion de los tokens
        - Sin @AutoWired por parametros: JwtTokenUtils, AuthenticationManager
        - Se implementa: UsernamePasswordAuthenticationFilter()
        - NO SALE AUTOMATICAMENTE: Se sobreescriben las funciones
            - attemptAuthentication()
            - successfulAuthentication()
            - unsuccessfulAuthentication()

    - JwtAuthorizationFilter: Configuramos la autorizacion de los usuarios
        - Sin @AutoWired por parametros: JwtTokenUtils, UsuarioService, AuthenticationManager [especifico de esta clase]
        - Se implementa: BasicAuthenticationFilter(authManager)
        - NO SALE AUTOMATICAMENTE: Se sobreescriben las funciones
            -  doFilterInternal()
        - Realizamos la funcion getAuthentication(token)

    - SecurityConfig: Configuramos el acceso a la API a sus END_POINT, haciendo uso de los elementos anteriores
        - La clase contara con las anotaciones:
            @Configuration
            @EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
        - Se inyecta por parametros: UsuarioService, JwtTokenUtils
        - Se genera un @Bean authManager
        - Se genera otro @Bean filterChain

9. Se genera el controlador
    - Lo mas destacable es, que ademas de inyectar los servicios necesarios, se inyecta AuthenticationManager y
    JwtTokenUtils

    - En las funciones que necesiten autenticacion se escribira lo siguiente por parametro:
    (@AuthenticationPrincipal user: Usuario)

    - En las funciones que se requiera de un ROl minimo, se escribira la siguiente anotacion:
        @PreAuthorize("hasRole('X')") [1 rol]
        @PreAuthorize("hasAnyRole('X', 'XX')") [varios roles]

    Ademas de haberlo limitado en SecurityConfig

Dejo la coleccion de postman para la comprobacion de los END_POINT
 */