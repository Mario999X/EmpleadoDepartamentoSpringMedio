package resa.mario.empleadodepartamentospring2.repositories.empleado

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import resa.mario.empleadodepartamentospring2.models.Empleado

@Repository
interface EmpleadoRepository : CoroutineCrudRepository<Empleado, Long> {
}