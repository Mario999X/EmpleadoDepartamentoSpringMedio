package resa.mario.empleadodepartamentospring2.repositories.usuario

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import resa.mario.empleadodepartamentospring2.models.Usuario

@Repository
interface UsuarioRepository : CoroutineCrudRepository<Usuario, Long> {

    // Metodo usado para Spring Security
    suspend fun findByUsername(username: String): Usuario?
}