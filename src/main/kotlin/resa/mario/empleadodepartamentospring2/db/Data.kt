package resa.mario.empleadodepartamentospring2.db

import resa.mario.empleadodepartamentospring2.dto.UsuarioDTORegister
import resa.mario.empleadodepartamentospring2.models.Usuario

fun getUsersInit() = listOf(
    UsuarioDTORegister(
        username = "Mario111",
        password = "1234",
        role = Usuario.Role.ADMIN.name // "ADMIN"
    ),
    UsuarioDTORegister(
        username = "Alysys222",
        password = "1234",
        role = Usuario.Role.USER.name // "USER"
    )
)