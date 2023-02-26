package resa.mario.empleadodepartamentospring2.mappers

import resa.mario.empleadodepartamentospring2.dto.UsuarioDTORegister
import resa.mario.empleadodepartamentospring2.dto.UsuarioDTOResponse
import resa.mario.empleadodepartamentospring2.models.Usuario

fun Usuario.toDTO(): UsuarioDTOResponse {
    return UsuarioDTOResponse(
        username = username,
        role = role,
        createdAt = createdAt.toString()
    )
}

fun UsuarioDTORegister.toUsuario(): Usuario {
    return Usuario(
        username = username,
        password = password,
        role = role
    )
}