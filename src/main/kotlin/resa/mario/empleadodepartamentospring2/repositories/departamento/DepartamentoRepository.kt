package resa.mario.empleadodepartamentospring2.repositories.departamento

import resa.mario.empleadodepartamentospring2.models.Departamento
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartamentoRepository : CoroutineCrudRepository<Departamento, Long> {
}